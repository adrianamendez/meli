package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_STRING

data class AddressDto(
    @Json(name = "state_id")
    val stateId: String? = EMPTY_STRING,
    @Json(name = "state_name")
    val stateName: String? = EMPTY_STRING,
    @Json(name = "city_id")
    val cityId: String? = EMPTY_STRING,
    @Json(name = "city_name")
    val cityName: String? = EMPTY_STRING
)
