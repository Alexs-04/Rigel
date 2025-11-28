package atlix.logic.services;

import atlix.model.beans.Product;
import atlix.model.beans.Supplier;
import atlix.model.beans.sub.ProductSupplier;
import atlix.model.beans.sub.ProductSupplierId;
import atlix.model.repositories.ProductRepository;
import atlix.model.repositories.SupplierRepository;
import atlix.model.request.ProductRequest;
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
        Product product = repository.findByBarcode(barcode)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Limpiar relaciones primero
        product.getSuppliers().forEach(ps -> {
            ps.getSupplier().getProducts().remove(ps);
        });
        product.getSuppliers().clear();

        repository.delete(product);
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
    public void update(String barcode, ProductRequest request) {
        Product existingProduct = repository.findByBarcode(barcode)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Actualizar campos básicos
        existingProduct.setName(request.productDTO().name());
        existingProduct.setPrice(request.productDTO().price());
        existingProduct.setCategory(request.productDTO().category());
        existingProduct.setDescription(request.productDTO().description());

        // Manejar relaciones con proveedores (si se proporcionan en el request)
        if (request.supplierName() != null && request.price() != null) {
            updateSupplierRelations(existingProduct, request.supplierName(), request.price());
        }

        repository.save(existingProduct);
    }

    private void updateSupplierRelations(Product product, String supplierName, Double price) {
        Supplier supplier = supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        // Buscar relación existente o crear nueva
        ProductSupplier relation = product.getSuppliers().stream()
                .filter(ps -> ps.getSupplier().getName().equals(supplierName))
                .findFirst()
                .orElse(new ProductSupplier());

        if (relation.getId() == null) {
            relation.setId(new ProductSupplierId());
            relation.setProduct(product);
            relation.setSupplier(supplier);
            product.getSuppliers().add(relation);
            supplier.getProducts().add(relation);
        }

        relation.setPrice(price);
    }
}
