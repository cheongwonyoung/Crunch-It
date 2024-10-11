package com.kb.crunchit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.crunchit.service.JwtService;
import com.kb.crunchit.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Map<String, String> resultMap = new HashMap<>();
        try{
            if(requestURI.contains("/ws")){
                filterChain.doFilter(request, response);
                return;
            }
            if(requestURI.contains("/auth/authenticate")){
                String accessToken = jwtService.extractAccessToken(request);
                if(accessToken == null|| !jwtTokenUtil.validateToken(accessToken)){
                    throw new AuthenticationException("invalid token"){};
                }
                response.setStatus(HttpStatus.OK.value());
                return;
            }
            if(requestURI.contains("/auth")){
                filterChain.doFilter(request, response);
                return;
            }
            String accessToken ="";
            if(requestURI.contains("/notifications/subscribe")){
                accessToken= request.getParameter("token");
            }else{
                accessToken = jwtService.extractAccessToken(request);
            }

            if(accessToken == null|| !jwtTokenUtil.validateToken(accessToken)){
                throw new AuthenticationException("invalid token"){};
            }
            Authentication authentication = jwtService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request,response);
        }
        catch(AuthenticationException ae){
            SecurityContextHolder.clearContext();
            resultMap.put("result" , "FAIL");
            request.setAttribute("tokenexception", resultMap);
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            responseData.put("message", "UnAuthorized : " + ae.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(responseData));
        }

    }
}
