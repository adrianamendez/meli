package denise.mendez.domain.models

data class Seller(
    val id: Int,
    val powerSellerStatus: String,
    val carDealer: Boolean,
    val realEstateAgency: Boolean,
    val tags: List<String>
)