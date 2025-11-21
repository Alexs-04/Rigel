package atlix.controllers.rest.auth;

import atlix.config.security.jwt.JwtService;
import atlix.logic.services.UserService;
import atlix.model.repositories.RefreshTokenRepository;
import atlix.model.util.LoginRequest;
import atlix.logic.services.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService service;

    public AuthRestController(
            PasswordEncoder encoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            RefreshTokenRepository refreshTokenRepository,
            UserService service
    ) {
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.service = service;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        var user = service.internalFindByUsername(request.username());

        if (user == null || !encoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        String accessToken = jwtService.generateToken(user.getUsername());

        var refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "role", user.getRole(),
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
            }

            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            var user = service.internalFindByUsername(username);

            if (user == null || !jwtService.isTokenValid(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "username", user.getUsername(),
                    "role", user.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error validando token");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {

        String token = body.get("refreshToken");

        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (refreshTokenService.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token expirado");
        }

        var username = refreshToken.getUser().getUsername();

        var newAccessToken = jwtService.generateToken(username);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "username", username
        ));
    }
}