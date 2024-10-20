package com.kb.crunchit.service;

import com.auth0.jwt.JWT;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    private final JwtTokenUtil jwtTokenUtil;
    public void sendAccessToken(HttpServletResponse response, String accessToken){
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);

    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader("Authorization-refresh", refreshToken);
    }

    public String extractAccessToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String extractRefreshToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization-refresh");
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token){
        String email = String.valueOf(jwtTokenUtil.extractEmail(token));
        Integer userId = Integer.valueOf(jwtTokenUtil.extractUserId(token));
        String nickname = String.valueOf(jwtTokenUtil.extractNickname(token));
        CustomUserDetails principal = new CustomUserDetails(email, null, nickname, userId);
        return new UsernamePasswordAuthenticationToken(principal, token, null);
    }
}
