package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.auth.RegisterRequest;
import com.quiz.QUIZ_Share.dto.keycloak.AuthResponse;
import com.quiz.QUIZ_Share.dto.keycloak.KeycloakTokenResponse;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.exceptions.GlobalExceptionHandler;
import com.quiz.QUIZ_Share.exceptions.UnauthorizedException;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final S3Service s3Service;
    private final UserRepository userRepository;

    @Value("${spring.application.jwt.keycloak.url}")
    private String keycloakUrl;
    @Value("${spring.application.jwt.keycloak.client-id}")
    private String clientId;
    @Value("${spring.application.jwt.keycloak.client-secret}")
    private String clientSecret;

    public AuthResponse getAuthResponse(String username, String password) {
        log.info("username={}", username);
        if(username.isEmpty() || password.isEmpty()){
            throw new IllegalArgumentException("Username or password is null or blank");
        }
        String tokenUrl = buildTokenEndpoint("token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", username);
        form.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        try {
            ResponseEntity<KeycloakTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, KeycloakTokenResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

                return mapTokenResponse(response.getBody());
            } else {
                throw new IllegalArgumentException("Failed to get AuthResponse");
            }
        }catch (HttpClientErrorException.Unauthorized e) {
            log.warn("Authentication failed for user '{}': Invalid credentials", username);
            throw new UnauthorizedException("Invalid credentials");
        } catch (HttpClientErrorException e) {
            log.error("HTTP error from Keycloak: {} - {}", e.getStatusCode(), e.getMessage());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException("Invalid credentials");
            }
            throw new UnauthorizedException("Invalid credentials");
        } catch (Exception e) {
            log.error("Unexpected error during authentication: ", e);
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public void registerUser(RegisterRequest request, MultipartFile file) {

        String adminToken = getAdminToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("{} {} {} {}", request.getUsername(), request.getEmail(), request.getFirstName(), request.getLastName());

        Map<String, Object> user = Map.of(
                "username", request.getUsername(),
                "email", request.getEmail(),
                "firstName", request.getFirstName(),
                "lastName", request.getLastName(),
                "enabled", true
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(user, headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    "http://localhost:8080/admin/realms/quiz_share/users",
                    entity,
                    Void.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {

                User new_user = new User();
                new_user.setUsername(request.getUsername());
                new_user.setEmail(request.getEmail());
                new_user.setFirstName(request.getFirstName());
                new_user.setLastName(request.getLastName());

                String url=s3Service.uploadAndGetAddress(file);
                new_user.setImageUrl(url);

                userRepository.save(new_user);

                String location = response.getHeaders().getLocation().toString();
                String userId = location.substring(location.lastIndexOf("/") + 1);

                setPassword(userId, request.getPassword(), adminToken);
            }else{
                throw new BadRequestException("Unexpected error during registration");
            }
        }catch (HttpClientErrorException.Unauthorized e) {
            log.warn("Invalid credentials");
            throw new UnauthorizedException("Invalid credentials");
        } catch (HttpClientErrorException e) {
            log.error("HTTP error from Keycloak: {} - {}", e.getStatusCode(), e.getMessage());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException("Invalid credentials");
            }
            throw new UnauthorizedException("Invalid credentials");
        } catch (Exception e) {
            log.error("Unexpected error during registration: ", e);
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public void setPassword(String userId, String password, String adminToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "type", "password",
                "value", password,
                "temporary", false
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.put(
                "http://localhost:8080/admin/realms/quiz_share/users/" + userId + "/reset-password",
                request
        );
    }



    public String getAdminToken(){

        String url = buildTokenEndpoint("token");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    public String buildTokenEndpoint(String endpointType){
        String base = keycloakUrl;
        return base + "/protocol/openid-connect/" + endpointType;
    }

    public AuthResponse mapTokenResponse(KeycloakTokenResponse tokenResponse) {
        return AuthResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .expiresIn(tokenResponse.getExpiresIn())
                .tokenType(tokenResponse.getTokenType())
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) throws BadRequestException {
        log.info("Refresh attempt");
        String tokenUrl = buildTokenEndpoint("token");
        if(refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException("Refresh token is empty");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        try {
            ResponseEntity<KeycloakTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, KeycloakTokenResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                if (response.getBody() != null) {
                    return mapTokenResponse(response.getBody());
                } else {
                    throw new RuntimeException("Unexpected Refresh Response!");
                }
            }
            throw new RuntimeException("Unexpected Refresh Response!");
        }catch (HttpClientErrorException e){
            String msg = e.getResponseBodyAsString();
            log.warn("Token refresh failed with status {}: {}", e.getStatusCode(), msg);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new UnauthorizedException("Invalid grant!");
            }
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException("Invalid ClientId!");
            }
            throw new UnauthorizedException("Refresh failed!");
        }catch (Exception e) {
            log.error("Unexpected error during token refresh", e);
            throw new UnauthorizedException("Refresh failed!");
        }
    }
}