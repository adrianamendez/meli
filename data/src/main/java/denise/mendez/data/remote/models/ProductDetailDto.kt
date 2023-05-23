package denise.mendez.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.utils.EMPTY_STRING

@JsonClass(generateAdapter = true)
data class ProductDetailDto(
    val id: String? = EMPTY_STRING,
    val title: String? = EMPTY_STRING,
    @field:Json(name = "category_id")
    val idCategory: String? = EMPTY_STRING,
    val pictures: List<PictureDto>? = emptyList(),
    val permalink: String? = EMPTY_STRING,
    val description: ItemDescriptionDto = ItemDescriptionDto()
) {

    fun mapToDomain() = ProductDetails(
        id ?: EMPTY_STRING,
        title ?: EMPTY_STRING,
        pictures?.map { it.mapToDomain() } ?: emptyList(),
        permalink = permalink ?: EMPTY_STRING,
        description = description.mapToDomain()
    )
}