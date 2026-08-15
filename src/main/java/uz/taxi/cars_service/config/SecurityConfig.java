package uz.taxi.cars_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.taxi.cars_service.base.BaseURL;
import uz.taxi.cars_service.config.filter.AfterFilter;
import uz.taxi.cars_service.config.filter.BeforeFilter;
import uz.taxi.cars_service.config.handler.AccessHandler;
import uz.taxi.cars_service.config.handler.AuthHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final BeforeFilter beforeFilter;
    private final AfterFilter afterFilter;

    private final String[] PERMIT_URLS = new String[]{
            BaseURL.DOC_OPEN_API,
            "/public/**",
            BaseURL.API1 + BaseURL.CARS + "/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PERMIT_URLS)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(auth ->
                        auth
                                .accessDeniedHandler(new AccessHandler())
                                .authenticationEntryPoint(new AuthHandler()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(beforeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(afterFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
