package com.beyzataylan.bookify.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {

    @Value("${jwt.expiration}")
    private long expirationTime;


    private final SecretKey secretKey  ;

    public JWTUtils(){
        String secretString = "843567893696976453275974432697R634976738467R678T34865R6834R8763T4783786376645387456738657";
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }





    public String generateToken(UserDetails userDetails){
        /*
         subject: Token’ın kime ait olduğunu belirtir (kullanıcı adı).

        issuedAt: Token’ın oluşturulma zamanı.

        expiration: Token’ın ne zaman geçersiz olacağı.

        signWith(secretKey): Token’ı gizli anahtar ile imzalar.

        compact(): Token’ı string formatında döndürür.
        */
    return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact();
    }

    public String  extractUsername(String token){
        /*
        *Token içindeki subject bilgisini (yani username) döndürür.

        * */
        return extractClaims(token, Claims::getSubject);
    }


    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        /*
        *
        * Token’ı çözümleyip (parse edip) içindeki Claims objesini döndürür.

        claimsTFunction: Hangi bilgiyi istediğini belirtir (ör. getSubject, getExpiration).*/
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload());
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        /*
        *
        *Token’daki kullanıcı adı ile UserDetails’ten gelen kullanıcı adı aynı mı diye bakar.

        Ayrıca token’ın süresi dolmamış olmalı.
        * */
        final  String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        /*Token’ın son kullanma tarihi şimdiki zamandan önceyse → token geçersizdir.*/
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}