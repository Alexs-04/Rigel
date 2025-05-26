package atlix.model.beans

import atlix.model.enums.Category
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "products")
class ProductBean(
    @Id
    @Column(name = "bar_code")

    var barCode: Long = 0,
    var price: Double = 0.0,
    var amount: Int = 0,
    var name: String = "",
    var description: String = "",

    @Column(name = "registration_date")
    var registrationDate: LocalDate = LocalDate.now(),
    @Column(name = "registered_by")
    var registeredBy: String = "",

    @ElementCollection(targetClass = Category::class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "product_categories", joinColumns = [JoinColumn(name = "bar_code")])
    @Column(name = "category")
    var categories: MutableList<Category> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    var supplierBean: SupplierBean = SupplierBean()
){

}
