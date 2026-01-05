package korebit.logic.services;

import korebit.model.beans.Product;
import korebit.model.beans.Sale;
import korebit.model.beans.User;
import korebit.model.beans.sub.SaleDetail;
import korebit.model.repositories.ProductRepository;
import korebit.model.repositories.SaleRepository;
import korebit.model.response.SaleDetailDTO;
import korebit.model.response.UserDTO;
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


    public Sale registerSale(Long userId, List<SaleDetailDTO> items) {
        User user;

        try {
            user = UserDTO.toEntity(userService.findById(userId));
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }

        Sale sale = new Sale();
        sale.setUser(user);

        double total = 0;

        for (SaleDetailDTO dto : items) {
            Product product = productRepository.findById(dto.productId()).orElseThrow();

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(dto.quantity());
            detail.setUnitPrice(dto.unitPrice());
            detail.setSubtotal(dto.quantity() * dto.unitPrice());

            sale.getDetails().add(detail);
            total += detail.getSubtotal();
        }

        sale.setTotal(total);
        return repository.save(sale);
    }
}
