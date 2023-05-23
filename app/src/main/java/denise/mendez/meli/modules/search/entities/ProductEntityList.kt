package denise.mendez.meli.modules.search.entities

import denise.mendez.domain.models.Product
import java.io.Serializable

data class ProductEntityList(
    val productEntity: List<Product>?
) : Serializable
