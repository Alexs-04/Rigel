package atlix.logic.services;

import atlix.model.beans.Product;
import atlix.model.beans.Supplier;
import atlix.model.beans.sub.ProductSupplier;
import atlix.model.repositories.ProductRepository;
import atlix.model.repositories.SupplierRepository;
import atlix.model.response.ProductDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository repository, SupplierRepository supplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO, String supplierName, Double supplierPrice) {
        if (repository.findByBarcode(productDTO.barcode()).isPresent()) {
            throw new IllegalArgumentException("Barcode already exists or product already exists");
        }

        Supplier supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Product product = new Product();
        product.setBarcode(productDTO.barcode());
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setCategory(productDTO.category());
        product.setDescription(productDTO.description());

        ProductSupplier relation = new ProductSupplier();
        relation.setSupplier(supplier);
        relation.setProduct(product);
        relation.setPrice(supplierPrice); // o el precio del proveedor si aplica

        product.getSuppliers().add(relation);
        supplier.getProducts().add(relation);

        repository.save(product);
    }

    public List<ProductDTO> getProductBySupplier(String supplierName) {
        if (supplierRepository.findByName(supplierName).isEmpty()) {
            throw new IllegalArgumentException("Supplier not found");
        }

        return repository.findBySupplierName(supplierName)
                .stream()
                .map(ProductDTO::toDTO)
                .toList();
    }

    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductDTO::toDTO)
                .toList();
    }

  @Transactional
  public void deleteByBarcode(String barcode) {
        repository.findByBarcode(barcode).orElseThrow(() -> new IllegalArgumentException("Barcode not found"));
        repository.deleteByBarcode(barcode);
    }

    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public ProductDTO findByName(String name) {
        return repository.findByName(name)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public ProductDTO findByBarcode(String barcode) {
        return repository.findByBarcode(barcode)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public Map<?,?> update(Product changes, String barcode) {
        var p = repository.findByBarcode(barcode);

        if (p.isEmpty()) {
            return Map.of(
                    "status",500,
                    "message", "Product not found"
            );
        }

        var existingProduct = p.get();

        existingProduct.setName(changes.getName());
        existingProduct.setBarcode(changes.getBarcode());
        existingProduct.setPrice(changes.getPrice());
        existingProduct.setCategory(changes.getCategory());

        existingProduct.getSuppliers().clear();
        existingProduct.getSuppliers().addAll(changes.getSuppliers());

        changes.getSuppliers().forEach(ps -> ps.setProduct(existingProduct));

        return Map.of(
                "status", 200,
                "message", "Product updated"
        );
    }
}
