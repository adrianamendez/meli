package denise.mendez.data.remote.models

import denise.mendez.domain.utils.EMPTY_STRING

data class SellerAddressDto(
    val country: CountryDto? = CountryDto(),
    val state: StateDto? = StateDto(),
    val city: CityDto? = CityDto(),
    val latitude: String? = EMPTY_STRING,
    val longitude: String? = EMPTY_STRING
)