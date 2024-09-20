package com.kb.crunchit.security;

import com.kb.crunchit.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.context.ContextLoaderListener;


public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationProvider(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if(jwtTokenUtil.validateToken(token)){
            System.out.println("123123");
            Claims claims = jwtTokenUtil.extractClaims(token);
            UserDetails userDetails = User.withUsername(claims.getSubject()).build();
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
