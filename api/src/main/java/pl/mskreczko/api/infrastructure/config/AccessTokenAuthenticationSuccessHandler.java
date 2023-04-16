package pl.mskreczko.api.infrastructure.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.mskreczko.api.infrastructure.config.jwt.AccessTokenProvider;
import pl.mskreczko.api.domain.user.service.UserJWTDetails;

import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccessTokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessTokenProvider accessTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final UserJWTDetails user = (UserJWTDetails) authentication.getPrincipal();
        final String accessToken = accessTokenProvider.getAccessToken(user.getId(), user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        response.setHeader("access_token", accessToken);
    }
}
