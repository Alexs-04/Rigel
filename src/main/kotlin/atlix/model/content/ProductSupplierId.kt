package atlix.model.content

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class ProductSupplierId(
    @Column(name = "product_id")
    var productId: Long = 0,

    @Column(name = "supplier_id")
    var supplierId: Int = 0
) : java.io.Serializable
