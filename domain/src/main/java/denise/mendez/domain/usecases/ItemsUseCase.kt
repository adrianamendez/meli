package denise.mendez.domain.usecases

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.models.ProductDetails
import kotlinx.coroutines.flow.Flow

interface ItemsUseCase {
    suspend fun getItemDescription(idProduct: String): Flow<ResourceState<ItemDescription>>

    suspend fun getItemProductDetail(idProduct: String): Flow<ResourceState<ProductDetails>>

    suspend fun getItemProductDetailWithDescription(idProduct: String): Flow<ResourceState<ProductDetails>>
}