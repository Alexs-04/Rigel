package atlix.model.beans

import atlix.model.content.ProductSupplier
import jakarta.persistence.*

@Entity
@Table(name = "products")
class ProductBean(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "bar_code", unique = true)
    var barCode: Long = 0,
    var price: Double = 0.0,
    var stock: Int = 0,
    var name: String = "",
    var description: String = "",

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var suppliers: MutableSet<ProductSupplier> = mutableSetOf()
)