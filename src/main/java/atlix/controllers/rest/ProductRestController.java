package atlix.controllers.rest;

import atlix.model.util.ProductDTO;
import atlix.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void addProduct(@RequestBody ProductDTO product) {
        productService.saveProduct(product);
    }
}
