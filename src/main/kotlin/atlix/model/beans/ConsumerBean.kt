package atlix.model.beans

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "consumers")
class ConsumerBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    var name: String = "",

    @Column(name = "consumer_name", nullable = false, unique = true)
    var consumerName: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(name = "register_date", nullable = false)
    var registerDate: LocalDate = LocalDate.now()
)
