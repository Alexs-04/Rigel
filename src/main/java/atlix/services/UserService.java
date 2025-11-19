package atlix.services;

import atlix.model.beans.User;
import atlix.model.repositories.UserRepository;
import atlix.model.util.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO save(UserDTO user) {
        // validar existencia del username
        if (repository.findByUsername(user.username()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        var entity = UserDTO.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.pass()));
        var saved = repository.save(entity);

        return UserDTO.toDTO(saved);
    }

    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDTO::toDTO)
                .toList();
    }

    public UserDTO findById(Long id) {
        return UserDTO.toDTO(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!")));
    }

    public UserDTO findByName(String name) {
        return UserDTO.toDTO(repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    @Transactional
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name()) // revisar si tu enum ya incluye "ROLE_" para evitar duplicados
                .build();
    }

    public UserDTO findByUsername(String username) {
        return UserDTO.toDTO(repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")));
    }
}