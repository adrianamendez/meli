package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import denise.mendez.domain.utils.EMPTY_INT


data class PagingDto(
    val total: Int? = EMPTY_INT,
    val offset: Int? = EMPTY_INT,
    val limit: Int? = EMPTY_INT,
    @Json(name = "primary_results")
    val primaryResults: Int? = EMPTY_INT
)
