package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import denise.mendez.domain.models.Picture

@JsonClass(generateAdapter = true)
data class PictureDto(
    val id: String,
    @field:Json(name = "secure_url")
    val url: String
) {

    fun mapToDomain() = Picture(id, url)
}