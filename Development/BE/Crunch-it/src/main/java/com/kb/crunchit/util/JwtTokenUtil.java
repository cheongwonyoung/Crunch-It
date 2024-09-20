package com.kb.crunchit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.ibatis.io.Resources;

import java.io.Reader;
import java.util.Properties;

public class JwtTokenUtil {
    private String secretKey;
    private int expiredTime;

    public JwtTokenUtil(){
        Properties properties = new Properties();
        try{
            Reader reader = Resources.getResourceAsReader("properties/jwt.properties");
            properties.load(reader);
            this.secretKey = properties.getProperty("jwt.secret");
            this.expiredTime = Integer.parseInt(properties.getProperty("jwt.expiredTime"));
        }catch(Exception ignored){

        }
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token){
        try{
            this.extractClaims(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
