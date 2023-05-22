package denise.mendez.domain.models

data class SellerAddress(
    val country: Country,
    val state: State,
    val city: City,
    val latitude: String,
    val longitude: String
)