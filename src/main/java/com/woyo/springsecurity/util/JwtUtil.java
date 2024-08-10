package com.woyo.springsecurity.util;

import com.woyo.springsecurity.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    @Value("${token.private-key}")
    private String privateKey;
    @Value("${token.public-key}")
    private String publicKey;
    @Value("${token.expired-time.access-token}")
    private Integer expiredAccessToken;
    @Value("${token.expired-time.refresh-token}")
    private Integer expiredRefreshToken;
    private final HttpServletRequest httpRequest;

    private final String BEARER = "Bearer ";

    public String generateToken(Map<String, Object> claims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationAt = new Date(System.currentTimeMillis() + expiredAccessToken);

        return Jwts.builder()
                .issuedAt(issuedAt)
                .expiration(expirationAt)
                .claims(claims)
                .signWith(getPrivateKey(), Jwts.SIG.RS512)
                .compact();
    }

    public  <T> T extractClaim(@NonNull final String token, @NonNull final Function<Claims, T> claimsResolver) {
        final var claims = Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    public List<GrantedAuthority> getAuthorityFromToken(String token) {
        ArrayList<String> authorities = extractClaim(token, claims -> claims.get("authorities", ArrayList.class));
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    private PrivateKey getPrivateKey() {
        try {
            byte[] decodedPrivateKey = Decoders.BASE64.decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedPrivateKey);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            log.info("NoSuchAlgorithmException error= {} ", noSuchAlgorithmException.getMessage());
            throw new CustomException("3000", noSuchAlgorithmException.getMessage());
        } catch (InvalidKeySpecException invalidKeySpecException) {
            log.info("InvalidKeySpecException: {} ", invalidKeySpecException.getMessage());
            throw new CustomException("3000", invalidKeySpecException.getMessage());
        }
    }

    private PublicKey getPublicKey() {
        try {
            byte[] decodedPrivateKey = Decoders.BASE64.decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedPrivateKey);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            log.info("NoSuchAlgorithmException error= {} ", noSuchAlgorithmException.getMessage());
            throw new CustomException("3000", noSuchAlgorithmException.getMessage());
        } catch (InvalidKeySpecException invalidKeySpecException) {
            log.info("InvalidKeySpecException: {} ", invalidKeySpecException.getMessage());
            throw new CustomException("3000", invalidKeySpecException.getMessage());
        }
    }
}
