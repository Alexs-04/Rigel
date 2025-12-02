package atlix.logic.services;

import atlix.model.beans.Product;
import atlix.model.beans.Sale;
import atlix.model.beans.User;
import atlix.model.beans.sub.SaleDetail;
import atlix.model.repositories.ProductRepository;
import atlix.model.repositories.SaleRepository;
import atlix.model.response.SaleDetailDTO;
import atlix.model.response.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private final SaleRepository repository;
    private final UserService userService;

    private final ProductRepository productRepository;

    public SaleService(SaleRepository repository, UserService userService, ProductRepository productRepository) {
        this.repository = repository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveSale(Sale sale) {
        repository.save(sale);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Sale findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Sale> findAll() {
        return repository.findAll();
    }


    @Transactional
    public Sale registerSale(Long userId, List<SaleDetailDTO> items) {
        if (userId == null) {
            throw new IllegalArgumentException("El id de usuario es requerido");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un item");
        }

        // Buscar usuario de manera segura
        User userEntity = null;
        try {
            userEntity = UserDTO.toEntity(userService.findById(userId));
        } catch (Exception e) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Sale sale = new Sale();
        sale.setUser(userEntity);
        sale.setDataTime(java.time.LocalDateTime.now());

        double total = 0.0;

        for (SaleDetailDTO dto : items) {
            if (dto == null) continue;

            if (dto.quantity() == null || dto.quantity() <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para el producto " + dto.productId());
            }

            Product product = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + dto.productId()));

            // Validar stock disponible
            Integer currentStock = product.getStock() == null ? 0 : product.getStock();
            if (currentStock < dto.quantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto " + dto.productId() + ". Disponible: " + currentStock + ", requerido: " + dto.quantity());
            }

            // Decrementar stock
            product.setStock(currentStock - dto.quantity());
            productRepository.save(product);

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(dto.quantity());
            // Si unitPrice viene null, usar price del producto
            double unitPrice = (dto.unitPrice() == null) ? (product.getPrice() == null ? 0.0 : product.getPrice()) : dto.unitPrice();
            detail.setUnitPrice(unitPrice);
            detail.setSubtotal(dto.quantity() * unitPrice);

            sale.getDetails().add(detail);
            total += detail.getSubtotal();
        }

        sale.setTotal(total);
        return repository.save(sale);
    }
}
