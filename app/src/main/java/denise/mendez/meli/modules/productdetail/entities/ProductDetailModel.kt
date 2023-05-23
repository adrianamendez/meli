package denise.mendez.meli.modules.productdetail.entities

import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.utils.EMPTY_STRING
import java.io.Serializable

data class ProductDetailModel(
    val id: String,
    val title: String,
    val pictures: List<PictureModel>,
    var description: String,
    var permalink: String
) : Serializable {

    companion object {

        fun mapFromDomain(productDetailsEntity: ProductDetails) = ProductDetailModel(
            productDetailsEntity.id,
            productDetailsEntity.title,
            productDetailsEntity.pictures.map { PictureModel.mapFromDomain(it) },
            productDetailsEntity.description?.plainText ?: EMPTY_STRING,
            productDetailsEntity.permalink
        )
    }
}
