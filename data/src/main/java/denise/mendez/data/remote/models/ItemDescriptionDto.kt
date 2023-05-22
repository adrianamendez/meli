package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import denise.mendez.domain.models.ItemDescription


@JsonClass(generateAdapter = true)
data class ItemDescriptionDto(
    val text: String,
    @field:Json(name = "plain_text")
    val plainText: String
) {
    fun mapToDomain() = ItemDescription(text, plainText)
}