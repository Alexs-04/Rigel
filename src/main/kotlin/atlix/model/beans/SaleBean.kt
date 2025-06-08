package atlix.model.beans

import atlix.model.content.SaleDescription
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "sales")
class SaleBean(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var date: LocalDate = LocalDate.now(),
    @Column(length = 10)
    var total: Double = 0.0,

    @OneToMany(mappedBy = "sale", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    var details: List<SaleDescription> = mutableListOf()
)