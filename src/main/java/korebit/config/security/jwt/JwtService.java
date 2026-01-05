package korebit.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret:mySuperSecretKeyThatIsAtLeast32CharactersLong!}") String secret) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }

        String base64Secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(base64Secret.getBytes());
    }

    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public String generateToken(Map<String, Object> extraClaims, String username) {
       long expirationTime = 1000 * 60 * 60; // 1 hora
        return Jwts.builder()
             .setClaims(extraClaims)
             .setSubject(username)
             .setIssuedAt(new Date(System.currentTimeMillis()))
             .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
             .signWith(secretKey, SignatureAlgorithm.HS256)
             .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {

            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is null or empty");
            }

            long dotCount = token.chars().filter(ch -> ch == '.').count();

            if (dotCount != 2) {
                throw new IllegalArgumentException("Invalid JWT format - expected 2 dots, found: " + dotCount);
            }

            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw e; // Relanzar para que el filter lo capture
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isTokenValid(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username)) && !isTokenExpired(token);
        } catch (Exception e) {
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}