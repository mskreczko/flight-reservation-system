package pl.mskreczko.api.infrastructure.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class AccessTokenPreAuthorizationFilter extends OncePerRequestFilter {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException  {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring("Bearer ".length());
            SecurityContextHolder.getContext().setAuthentication(accessTokenProvider.extractAuthentication(token));
        }

        filterChain.doFilter(request, response);
    }
}
