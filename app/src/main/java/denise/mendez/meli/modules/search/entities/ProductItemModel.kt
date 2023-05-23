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
    val hasShippingGuaranteed: Boolean = false,
    val condition: String = EMPTY_STRING
) : Serializable {

    companion object {
        fun mapFromDomain(product: Product) = ProductItemModel(
            id = product.id,
            title = product.title,
            price = product.price,
            quantity = product.availableQuantity,
            soldQuantity = product.soldQuantity,
            thumbnail = product.thumbnail,
            freeShipping = product.shipping.freeShipping,
            hasFulfillment = product.shipping.storePickUp,
            hasShippingGuaranteed = product.shipping.logisticType == "fulfillment",
            condition = product.condition
        )
    }
}
