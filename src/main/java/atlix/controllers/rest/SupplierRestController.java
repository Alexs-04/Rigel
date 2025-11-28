package atlix.controllers.rest;

import atlix.logic.services.SupplierService;
import atlix.model.response.SupplierDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierRestController {

    private final SupplierService supplierService;

    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    public List<SupplierDTO> getSuppliers() {
        return supplierService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestBody SupplierDTO dto) {
        supplierService.save(dto);
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Supplier added successfully"
        ));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteSupplier(@RequestBody SupplierDTO request) {
        supplierService.delete(request);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Supplier deleted successfully"
        ));
    }
}