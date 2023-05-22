package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_INT
import denise.mendez.domain.utils.EMPTY_STRING

data class SellerDto(
    val id: Int? = EMPTY_INT,
    @Json(name = "power_seller_status")
    val powerSellerStatus: String? = EMPTY_STRING,
    @Json(name = "car_dealer")
    val carDealer: Boolean? = false,
    @Json(name = "real_estate_agency")
    val realEstateAgency: Boolean? = false,
    val tags: List<String>? = emptyList()
)