package com.swapnil.bustracker.config;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="mysecretkeymysecretkeymysecretkey";

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    public String generateToken(String email){
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+1000*60*60)).signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractEmail(String token ){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken (String token , String email){
        String extracted = extractEmail(token);
        return extracted.equals(email);
    }

}
