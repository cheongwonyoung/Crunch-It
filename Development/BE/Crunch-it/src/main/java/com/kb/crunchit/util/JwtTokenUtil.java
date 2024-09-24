package com.kb.crunchit.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Component
@Slf4j
public class JwtTokenUtil {
    private String secretKey;
    private Long accessTokenExpirationPeriod;

    private Long refreshTokenExpirationPeriod;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";


    public JwtTokenUtil(){
        Properties properties = new Properties();
        try{
            Reader reader = Resources.getResourceAsReader("properties/jwt.properties");
            properties.load(reader);
            this.secretKey = properties.getProperty("jwt.secret");
            this.accessTokenExpirationPeriod = Long.parseLong(properties.getProperty("jwt.access.expiration"));
            this.refreshTokenExpirationPeriod = Long.parseLong(properties.getProperty("jwt.refresh.expiration"));
        }catch(Exception ignored){

        }
    }

    public String createAccessToken(String email){
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String createRefreshToken(){
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Optional<String> extractEmail(String accessToken){
        try{
            return Optional.ofNullable(JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch(Exception e){
            return Optional.empty();
        }
    }

    public Boolean validateToken(String token){
        try{
            JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
