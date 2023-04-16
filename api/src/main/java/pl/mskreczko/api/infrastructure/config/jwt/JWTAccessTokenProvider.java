package pl.mskreczko.api.infrastructure.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JWTAccessTokenProvider implements AccessTokenProvider {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTAccessTokenProvider() {
        this.algorithm = Algorithm.HMAC256("not a secret".getBytes(StandardCharsets.UTF_8));
        this.verifier = JWT.require(algorithm).build();
    }

    @Override
    public String getAccessToken(UUID userId, List<String> roles) {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    @Override
    public UsernamePasswordAuthenticationToken extractAuthentication(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return new UsernamePasswordAuthenticationToken(
                jwt.getSubject(),
                null,
                jwt.getClaim("roles").asList(String.class).stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
