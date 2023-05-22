package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import denise.mendez.domain.models.ProductDetails

@JsonClass(generateAdapter = true)
data class ProductDetailDto(
    val id: String,
    val title: String,
    @field:Json(name = "category_id")
    val idCategory: String,
    val pictures: List<PictureDto>,
    val permalink: String,
    val descriptionDto: ItemDescriptionDto
) {

    fun mapToDomain() = ProductDetails(
        id,
        title,
        pictures.map { it.mapToDomain() },
        permalink = permalink,
        description = descriptionDto.mapToDomain()
    )
}