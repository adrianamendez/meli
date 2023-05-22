package denise.mendez.domain.usecases

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.models.Product
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.repositories.ItemsRepository
import denise.mendez.domain.repositories.SitesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ItemsUseCaseImpl @Inject constructor(private val itemsRepository: ItemsRepository) :
    ItemsUseCase {
    override suspend fun getItemDescription(idItem: String): Flow<ResourceState<ItemDescription>> =
        itemsRepository.getItemDescription(idItem)

    override suspend fun getItemProductDetail(idProduct: String): Flow<ResourceState<ProductDetails>> =
        itemsRepository.getItemProductDetail(idProduct)

}