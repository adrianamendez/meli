package denise.mendez.domain.usecases

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsUseCaseImpl @Inject constructor(private val itemsRepository: ItemsRepository) :
    ItemsUseCase {
    override suspend fun getItemDescription(idProduct: String): Flow<ResourceState<ItemDescription>> =
        itemsRepository.getItemDescription(idProduct)

    override suspend fun getItemProductDetail(idProduct: String): Flow<ResourceState<ProductDetails>> =
        itemsRepository.getItemProductDetail(idProduct)

    override suspend fun getItemProductDetailWithDescription(idProduct: String): Flow<ResourceState<ProductDetails>> = itemsRepository.getItemProductDetailWithDescription(idProduct)
}