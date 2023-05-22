package denise.mendez.domain.models

data class Paging(
    val total: Int,
    val offset: Int,
    val limit: Int,
    val primaryResults: Int
)