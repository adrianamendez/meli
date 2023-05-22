package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_STRING


data class ProductSearchedDto(
    @Json(name = "site_id")
    val siteId: String? = EMPTY_STRING,
    val query: String? = EMPTY_STRING,
    val paging: PagingDto? = PagingDto(),
    val results: List<ProductDto>? = emptyList()
)