package atlix.logic.services


import atlix.model.beans.SaleBean
import atlix.model.content.SaleDescription
import atlix.model.repository.SaleRepository
import java.time.LocalDate


class SaleService {

    private val saleRepository: SaleRepository = SaleRepository()

    fun getAllSales(): List<SaleBean> {
        return saleRepository.findAll()
    }



    fun getSaleById(id: Long): SaleBean {
        return saleRepository.findById(id) ?: throw NoSuchElementException("Sale with id $id not found")
    }

    fun saveSaleWithDescriptions(
        date: LocalDate,
        total: Double,
        descriptions: List<SaleDescription>
    ) {
        val saleBean = SaleBean(
            date = date,
            total = total,
            details = mutableListOf()
        )

        // Asigna la venta a cada descripción
        descriptions.forEach { it.sale = saleBean }

        // Añade las descripciones a la venta
        saleBean.details = descriptions.toMutableList()

        saleRepository.save(saleBean)
    }

}