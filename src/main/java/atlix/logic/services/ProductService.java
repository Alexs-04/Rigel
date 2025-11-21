package atlix.logic.services;

import atlix.model.repositories.ProductRepository;
import atlix.model.util.ProductDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveProduct(ProductDTO product) {
        repository.save(ProductDTO.toEntity(product));
    }

    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductDTO::toDTO)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDTO findByName(String name) {
        return repository.findByName(name)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
