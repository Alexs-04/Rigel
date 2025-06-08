package atlix.model.beans

import atlix.model.content.ProductSupplier
import jakarta.persistence.*

@Entity
@Table(name = "suppliers")
class SupplierBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var company: String = "",
    var name: String = "",
    var nameContact: String = "",

    @Column(name = "number_phone")
    var numberPhone: String = "",

    var email: String = "",
    @OneToMany(mappedBy = "supplier", cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: MutableSet<ProductSupplier> = mutableSetOf()
)
