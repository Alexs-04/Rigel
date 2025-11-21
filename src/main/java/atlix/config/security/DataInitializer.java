package atlix.config.security;

import atlix.model.beans.User;
import atlix.model.enums.Role;
import atlix.model.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@Deprecated
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        String defaultUsername = "admin";
        String defaultPassword = "admin123";

        if (repository.findByUsername(defaultUsername).isEmpty()) {
            User u = new User();
            u.setName("admin");
            u.setUsername(defaultUsername);
            u.setPassword(encoder.encode(defaultPassword));
            u.setEmail("admin@example.com");
            u.setRole(Role.ADMIN);
            u.setAddress("Mx");
            u.setPhone("52");

            repository.save(u);
        }
    }
}
