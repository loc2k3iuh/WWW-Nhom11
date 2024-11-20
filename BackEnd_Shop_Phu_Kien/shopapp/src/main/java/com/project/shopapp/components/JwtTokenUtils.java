package com.project.shopapp.components;

import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

//  Environment variable.
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) throws InvalidParamException {
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try{
            String token =  Jwts.builder()
                    .setClaims(claims) //
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e){
            throw new InvalidParamException("Can not create token, error: " + e.getMessage());
        }
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey); // xR07CNZWi8ZBd5J28ct1XLixSYFEUABssrDroJmK2n4=
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String sercetKey = Encoders.BASE64.encode(bytes);
        return sercetKey;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println("Can not extract claims, error: " + e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    Check expiration of token.
    public Boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = this.extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
