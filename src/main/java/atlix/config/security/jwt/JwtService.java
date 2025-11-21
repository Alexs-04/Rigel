package atlix.config.security.jwt;

import io.jsonwebtoken.Claims;
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
        // Asegúrate de que la clave tenga al menos 32 caracteres
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }

        // Codifica la clave en base64 para asegurar la longitud correcta
        String base64Secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(base64Secret.getBytes());

        System.out.println("=== JWT SERVICE CONFIGURADO ===");
        System.out.println("Secret length: " + secret.length());
    }

    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public String generateToken(Map<String, Object> extraClaims, String username) {
        long expirationTime = 1000 * 60 * 60; // 1 hora

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("=== JWT GENERADO ===");
        System.out.println("Para usuario: " + username);
        System.out.println("Expira en: " + new Date(System.currentTimeMillis() + expirationTime));

        return token;
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
            System.out.println("=== ANALIZANDO JWT ===");

            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is null or empty");
            }

            long dotCount = token.chars().filter(ch -> ch == '.').count();
            System.out.println("Número de puntos: " + dotCount);

            if (dotCount != 2) {
                throw new IllegalArgumentException("Invalid JWT format - expected 2 dots, found: " + dotCount);
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("JWT válido para usuario: " + claims.getSubject());
            System.out.println("Expira: " + claims.getExpiration());
            return claims;

        } catch (Exception e) {
            System.out.println("ERROR analizando JWT: " + e.getMessage());
            throw e;
        }
    }

    public boolean isTokenValid(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            boolean isValid = (extractedUsername.equals(username)) && !isTokenExpired(token);
            System.out.println("Token válido: " + isValid);
            return isValid;
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