package denise.mendez.data.repository

import android.util.Log
import denise.mendez.data.mappers.SuccessItemDescriptionMapper
import denise.mendez.data.mappers.SuccessItemProductDetailMapper
import denise.mendez.data.network.map
import denise.mendez.data.network.message
import denise.mendez.data.network.suspendOnFailure
import denise.mendez.data.network.suspendOnSuccess
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.ItemDescription
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.repositories.ItemsRepository
import denise.mendez.domain.utils.REPOSITORY_ITEMS
import denise.mendez.domain.utils.REPOSITORY_PRODUCT_DETAIL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class ItemsRepositoryImpl(private val meliApi: MeliApi) : ItemsRepository {
    override suspend fun getItemDescription(idItem: String): Flow<ResourceState<ItemDescription>> = flow<ResourceState<ItemDescription>> {
        val response = meliApi.getItemDescription(idItem)
        response.suspendOnSuccess {
            val itemDescription = map(SuccessItemDescriptionMapper)
            if (itemDescription == null) {
                emit(ResourceState.Error(message = "An error occurred while mapping SuccessItemDescriptionMapper"))
                Log.d(REPOSITORY_ITEMS,"An error occurred while mapping SuccessItemDescriptionMapper returns Null")
            } else {
                emit(ResourceState.Success(itemDescription))
                Log.d(REPOSITORY_ITEMS, "Success")
            }
        }.suspendOnFailure {
            emit(ResourceState.Error(message = message()))
            Log.d(REPOSITORY_ITEMS, message())
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun getItemProductDetail(idProduct: String): Flow<ResourceState<ProductDetails>> = flow<ResourceState<ProductDetails>> {
        val response = meliApi.getProductDetails(idProduct)
        response.suspendOnSuccess {
            val itemDescription = map(SuccessItemProductDetailMapper)
            if (itemDescription == null) {
                emit(ResourceState.Error(message = "An error occurred while mapping SuccessItemDescriptionMapper"))
                Log.d(REPOSITORY_PRODUCT_DETAIL, "An error occurred while mapping SuccessItemDescriptionMapper Returns Null")
            } else {
                emit(ResourceState.Success(itemDescription))
                Log.d(REPOSITORY_PRODUCT_DETAIL, "Success")
            }
        }.suspendOnFailure {
            emit(ResourceState.Error(message = message()))
            Log.d(REPOSITORY_PRODUCT_DETAIL, message())
        }
    }.flowOn(Dispatchers.Default)
}