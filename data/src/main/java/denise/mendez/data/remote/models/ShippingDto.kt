package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_STRING

data class ShippingDto(
    @Json(name = "free_shipping")
    val freeShipping: Boolean? = false,
    val mode: String? = EMPTY_STRING,
    val tags: List<String>? = emptyList(),
    @Json(name = "logistic_type")
    val logisticType: String? = EMPTY_STRING,
    @Json(name = "store_pick_up")
    val storePickUp: Boolean? = false
)
