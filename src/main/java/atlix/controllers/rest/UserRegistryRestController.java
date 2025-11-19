package atlix.controllers.rest;

import atlix.model.enums.Role;
import atlix.model.util.UserDTO;
import atlix.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/users")
@RestController
public class UserRegistryRestController {

    private final UserService userService;

    public UserRegistryRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO request) {
        var user = userService.findByUsername(request.username());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", 201,
                "message", "User added successfully"
        ));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO request, @RequestParam Role role) {
        if (role != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Role not allowed");
        }

        var user = userService.findByUsername(request.username());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Usar el método de borrado disponible en UserService.
        // Aquí se asume userService.delete(user) existe; si no, usar deleteByUsername(request.username()).
        userService.deleteByUsername(user.username());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "User deleted successfully"
        ));
    }
}