package korebit.model.repositories;

import korebit.model.beans.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    Optional<Product> findByBarcode(String barcode);

    void deleteByBarcode(String barcode);

    @Query("""
    SELECT p
    FROM Product p
    JOIN p.suppliers ps
    WHERE ps.supplier.name = :supplierName
""")
    List<Product> findBySupplierName(String supplierName);
}
