package atlix.controllers.rest.auth;

import atlix.config.security.jwt.JwtService;
import atlix.model.repositories.RefreshTokenRepository;
import atlix.model.util.LoginRequest;
import atlix.model.util.UserDTO;
import atlix.services.RefreshTokenService;
import atlix.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final UserService service;
    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthRestController(
            UserService service,
            PasswordEncoder encoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            RefreshTokenRepository refreshTokenRepository) {

        this.service = service;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        var user = service.findByUsername(request.username());
        if (user == null || !encoder.matches(request.password(), user.pass())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        String accessToken = jwtService.generateToken(user.username());

        // Crear refresh token
        var userEntity = UserDTO.toEntity(user);
        var refreshToken = refreshTokenService.createRefreshToken(userEntity);

        return ResponseEntity.ok(Map.of(
                "username", user.username(),
                "role", user.role(),
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        var user = service.findByUsername(username);

        if (!jwtService.isTokenValid(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        return ResponseEntity.ok(Map.of(
                "valid", true,
                "username", user.username(),
                "role", user.role()
        ));
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