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
import denise.mendez.domain.utils.REPOSITORY_PRODUCT_DETAIL_COMBINED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class ItemsRepositoryImpl(private val meliApi: MeliApi) : ItemsRepository {
    override suspend fun getItemDescription(idProduct: String): Flow<ResourceState<ItemDescription>> = flow<ResourceState<ItemDescription>> {
        val response = meliApi.getItemDescription(idProduct)
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
            val productDetail = map(SuccessItemProductDetailMapper)
            if (productDetail == null) {
                emit(ResourceState.Error(message = "An error occurred while mapping SuccessItemProductDetailMapper"))
                Log.d(REPOSITORY_PRODUCT_DETAIL, "An error occurred while mapping SuccessItemProductDetailMapper Returns Null")
            } else {
                emit(ResourceState.Success(productDetail))
                Log.d(REPOSITORY_PRODUCT_DETAIL, "Success")
            }
        }.suspendOnFailure {
            emit(ResourceState.Error(message = message()))
            Log.d(REPOSITORY_PRODUCT_DETAIL, message())
        }
    }.flowOn(Dispatchers.Default)


    override suspend fun getItemProductDetailWithDescription(idProduct: String): Flow<ResourceState<ProductDetails>> =
        getItemProductDetail(idProduct).combine(getItemDescription(idProduct)) { productDetailState, itemDescriptionState ->
            when {

                productDetailState is ResourceState.Success && itemDescriptionState is ResourceState.Error -> {
                    // If just one is successful return the successful one
                    Log.d(REPOSITORY_PRODUCT_DETAIL_COMBINED, "Only productDetail is Success")
                    ResourceState.Success(productDetailState.data)

                }
                productDetailState is ResourceState.Success && itemDescriptionState is ResourceState.Success -> {
                    // If both are successful, combine the results
                    val combinedDetail = productDetailState.data.apply { this?.description = itemDescriptionState.data }
                    Log.d(REPOSITORY_PRODUCT_DETAIL_COMBINED, "Both are Success")
                    ResourceState.Success(combinedDetail)
                }

                else -> {
                    Log.d(REPOSITORY_PRODUCT_DETAIL_COMBINED, "Both are Error")
                    ResourceState.Error("An unexpected error occurred")
                }
            }
        }.flowOn(Dispatchers.Default)

}