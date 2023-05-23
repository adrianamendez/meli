package denise.mendez.meli.modules.productdetail.entities

import denise.mendez.domain.models.Picture

data class PictureModel(
    val url: String
) {
    companion object {
        fun mapFromDomain(pictureEntity: Picture) = PictureModel(pictureEntity.url)
    }
}
