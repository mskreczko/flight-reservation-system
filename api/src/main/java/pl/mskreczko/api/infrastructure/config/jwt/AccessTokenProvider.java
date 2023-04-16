package pl.mskreczko.api.infrastructure.config.jwt;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface AccessTokenProvider {
    String getAccessToken(UUID userId, List<String> roles);
    Authentication extractAuthentication(String token);
}
