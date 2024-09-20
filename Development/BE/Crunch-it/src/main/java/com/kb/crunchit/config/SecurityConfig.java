package com.kb.crunchit.config;

import com.kb.crunchit.security.JwtAccessDeniedHandler;
import com.kb.crunchit.security.JwtAuthenticationEntryPoint;
import com.kb.crunchit.security.JwtAuthenticationFilter;
import com.kb.crunchit.security.JwtAuthenticationProvider;
import com.kb.crunchit.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtTokenUtil());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter("/**"); // 필터가 적용될 URL 패턴 지정
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> jwtAuthenticationProvider().authenticate(authentication);
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler(){
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler())
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/**")
                                ,new AntPathRequestMatcher("/login")).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
