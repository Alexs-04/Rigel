package atlix.model.content

import atlix.model.beans.ProductBean
import atlix.model.beans.SupplierBean
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Entity
@Table(name = "product_supplier")
class ProductSupplier(

    @EmbeddedId
    var id: ProductSupplierId = ProductSupplierId(),

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    var product: ProductBean? = null,

    @ManyToOne
    @MapsId("supplierId")
    @JoinColumn(name = "supplier_id")
    var supplier: SupplierBean? = null,

    @Column(name = "purchase_price")
    var purchasePrice: Double = 0.0
)
