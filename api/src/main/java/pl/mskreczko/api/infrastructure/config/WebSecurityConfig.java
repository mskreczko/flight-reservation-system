package pl.mskreczko.api.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pl.mskreczko.api.infrastructure.config.jwt.AccessTokenPreAuthorizationFilter;
import pl.mskreczko.api.infrastructure.config.jwt.AccessTokenProvider;
import pl.mskreczko.api.domain.user.service.UserAuthService;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserAuthService userAuthService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccessTokenProvider accessTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .addFilterBefore(new AccessTokenPreAuthorizationFilter(accessTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/unauthenticated/**").permitAll()
                        .requestMatchers("/api/v1/user/**").hasAuthority("ROLE_USER")
                        .anyRequest().denyAll())
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .securityContext((securityContext) -> securityContext.securityContextRepository(new RequestAttributeSecurityContextRepository()));

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authenticationProvider;
    }
}
