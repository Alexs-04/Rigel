package atlix.model.beans.sub;

import atlix.model.beans.Product;
import atlix.model.beans.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplier {
    @EmbeddedId
    private ProductSupplierId id = new ProductSupplierId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplierId") // Mapea el ID del proveedor desde la clave compuesta
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private double price;
}
