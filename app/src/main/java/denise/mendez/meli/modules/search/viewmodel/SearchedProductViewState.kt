package denise.mendez.meli.modules.search.viewmodel

import denise.mendez.domain.models.Product


data class SearchedProductViewState(
    val isLoading: Boolean = false,
    val getProducts: List<Product>? = null,
    val error: String? = null
)  {
    companion object {
        fun idle(): SearchedProductViewState = SearchedProductViewState(
            isLoading = true,
            getProducts = null,
            error = null
        )
    }
}