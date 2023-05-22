package denise.mendez.domain.models

data class ProductDetails(
    val id: String,
    val title: String,
    val pictures: List<Picture>,
    var description: ItemDescription?,
    val permalink: String
)