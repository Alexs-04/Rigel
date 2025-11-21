package atlix.logic.services;

import atlix.model.repositories.ProductRepository;
import atlix.model.repositories.SaleRepository;
import atlix.model.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final ProductRepository productRepository;

    private final SaleRepository saleRepository;

    private final SupplierRepository supplierRepository;

    public DashboardService(ProductRepository productRepository, SaleRepository saleRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.supplierRepository = supplierRepository;
    }

    public Map<?, ?> getDashboard() {
        return Map.of(
                "totalProducts", 20,
                "salesToday", 50,
                "totalSuppliers", 12,
                "totalOrders", 27,
                "monthlySales", List.of(),
                "purchasesBySupplier", List.of(),
                "systemDistribution", List.of()
        );
    }

    private int getTotalProducts() {
        return productRepository.findAll().size();
    }

    private int getTotalSales() {
        return saleRepository.findAll().size();
    }

    private int getTotalSuppliers() {
        return supplierRepository.findAll().size();
    }
}
