package denise.mendez.data.repository

import denise.mendez.data.mappers.SuccessItemProductSearchedMapper
import denise.mendez.data.network.SITE_ID
import denise.mendez.data.network.map
import denise.mendez.data.network.message
import denise.mendez.data.network.suspendOnFailure
import denise.mendez.data.network.suspendOnSuccess
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.Product
import denise.mendez.domain.repositories.SitesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class SitesRepositoryImpl(private val meliApi: MeliApi) : SitesRepository {
    override suspend fun getProducts(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<ResourceState<List<Product>>> = flow<ResourceState<List<Product>>> {
        val response = meliApi.searchProducts(SITE_ID, query, offset, limit)
        response.suspendOnSuccess {
            val itemDescription = map(SuccessItemProductSearchedMapper)
            if (itemDescription == null) {
                emit(ResourceState.Error(message = "An error occurred while mapping SuccessItemDescriptionMapper"))
            } else {
                emit(ResourceState.Success(itemDescription))
            }
        }.suspendOnFailure {
            emit(ResourceState.Error(message = message()))
        }
    }.flowOn(Dispatchers.Default)
}
