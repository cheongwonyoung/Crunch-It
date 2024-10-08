package com.kb.crunchit.security;

import com.kb.crunchit.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String email = principal.getUsername();
        String nickname = principal.getNickname();
        Integer userId = principal.getUserId();
        String accessToken = jwtTokenUtil.createAccessToken(email, nickname, userId);
        response.setHeader("Authorization", "Bearer " + accessToken);
        log.info("LoginSuccessHandler.onAuthenticationSuccess");
    }
}
