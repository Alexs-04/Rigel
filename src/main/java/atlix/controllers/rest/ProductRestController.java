package atlix.controllers.rest;

import atlix.model.beans.Product;
import atlix.model.enums.Role;
import atlix.model.response.ProductDTO;
import atlix.logic.services.ProductService;
import atlix.model.request.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<?> getProducts() {
        return productService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest product) {

        productService.saveProduct(product.productDTO(), product.supplierName(), product.price());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "product add successfully"
        ));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(@RequestBody ProductRequest changes,  @RequestParam("barcode") String barcode) {
        productService.update(barcode, changes);
        return ResponseEntity.ok(
                Map.of(
                        "status", 200,
                        "message", "product edited successfully"
                )
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ProductDTO product, @RequestParam Role role) {

        if (role != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Role not allowed");
        }

        productService.deleteByBarcode(product.barcode());

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "product deleted successfully"
        ));
    }
}
