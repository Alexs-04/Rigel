package atlix.logic.services;

import atlix.model.beans.RefreshToken;
import atlix.model.beans.User;
import atlix.model.repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {

        // Si ya tenía un token, lo borramos
        repository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusSeconds(60 * 60)); // 1 Hora

        return repository.save(token);
    }

    @Transactional
    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
}
