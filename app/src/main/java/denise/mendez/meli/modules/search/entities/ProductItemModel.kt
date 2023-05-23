package denise.mendez.meli.modules.search.entities

import denise.mendez.domain.models.Product
import denise.mendez.domain.utils.EMPTY_DOUBLE
import denise.mendez.domain.utils.EMPTY_STRING
import java.io.Serializable

data class ProductItemModel(
    val id: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val price: Double = EMPTY_DOUBLE,
    val quantity: Int = 0,
    val soldQuantity: Int = 0,
    val thumbnail: String = EMPTY_STRING,
    val freeShipping: Boolean = false,
    val hasFulfillment: Boolean = false,
    val hasShippingGuaranteed: Boolean = false
) : Serializable {

    companion object {
        fun mapFromDomain(product: Product) = ProductItemModel(
            product.id,
            product.title,
            //  product.soldQuantity,
            //  product.condition
        )
    }
}