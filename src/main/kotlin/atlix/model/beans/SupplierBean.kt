package atlix.model.beans

import atlix.model.content.Address
import jakarta.persistence.*

@Entity
@Table(name = "suppliers")
class SupplierBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var company: String = "",
    var name: String = "",

    @Embedded
    var address: Address = Address(),

    @Column(name = "number_phone")
    var numberPhone: String = "",

    @OneToMany(mappedBy = "supplierBean", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfProducts: List<ProductBean> = ArrayList()
){

}
