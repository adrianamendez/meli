package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.utils.EMPTY_STRING

@JsonClass(generateAdapter = true)
data class ItemDescriptionDto(
    val text: String? = EMPTY_STRING,
    @field:Json(name = "plain_text")
    val plainText: String? = EMPTY_STRING
) {
    fun mapToDomain() = ItemDescription(text ?: EMPTY_STRING, plainText ?: EMPTY_STRING)
}