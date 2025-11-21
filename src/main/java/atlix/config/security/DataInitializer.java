package atlix.config.security;

import atlix.config.security.jwt.JwtService;
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
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public DataInitializer(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void run(String... args) {
        String defaultUsername = "admin";
        String defaultPassword = "admin123"; // cambiar por uno más seguro en producción

        if (repository.findByUsername(defaultUsername).isEmpty()) {
            User u = new User();
            u.setName("admin");
            u.setUsername(defaultUsername);
            u.setPassword(passwordEncoder.encode(defaultPassword));
            u.setEmail("admin@a@a@a@a@a@a@a@a");
            u.setRole(Role.ADMIN);
            u.setAddress("Mx");
            u.setPhone("52");

            repository.save(u);
        }
    }
}
