package denise.mendez.domain.models

data class ProductSearched(
    val siteId: String,
    val query: String,
    val paging: Paging,
    val results: List<Product>
)