package com.kb.crunchit.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Map<String,String> map = (Map<String,String>) request.getAttribute("tokenexception");
        if(map ==null)
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        setResponse(response, 401, map.get("msg"));

    }

    private void setResponse(HttpServletResponse response, int code, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + msg
                + "\", \"code\" : \"" +  code
                + "\", \"status\" : " + "500"
                + ", \"errors\" : [ ] }");
    }

}
