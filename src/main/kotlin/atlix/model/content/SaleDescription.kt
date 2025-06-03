package atlix.model.content

import atlix.model.beans.ProductBean
import atlix.model.beans.SaleBean
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "sale_description")
class SaleDescription(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "sale_id")
    var sale: SaleBean,
    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: ProductBean,
    var amount: Int = 0,
    var price: Double = 0.0,
    var subtotal: Double = 0.0
)