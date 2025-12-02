package atlix.controllers.rest;

import atlix.logic.services.SaleService;
import atlix.logic.services.UserService;
import atlix.model.request.SaleItemRequest;
import atlix.model.request.SalePayloadRequest;
import atlix.model.response.SaleDetailDTO;
import atlix.model.response.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SalePointRestController {

    private final SaleService saleService;
    private final UserService userService;

    public SalePointRestController(SaleService saleService, UserService userService) {
        this.saleService = saleService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSale(@RequestBody SalePayloadRequest payload) {
        try {
            if (payload == null || payload.items() == null || payload.items().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payload inválido: items requeridos");
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
            }

            Object principal = auth.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                username = (String) principal;
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario inválido en contexto de seguridad");
            }

            // Obtener user id a partir del username
            var userEntity = userService.internalFindByUsername(username);
            Long userId = userEntity.getId();

            List<SaleDetailDTO> items = payload.items().stream()
                    .map(i -> new SaleDetailDTO(i.productId(), i.qty(), i.unitPrice()))
                    .collect(Collectors.toList());

            var sale = saleService.registerSale(userId, items);

            return ResponseEntity.status(HttpStatus.CREATED).body(sale);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creando la venta: " + e.getMessage());
        }
    }
}
