package denise.mendez.domain.usecases

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface SitesUseCase {

    suspend fun getProducts(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<ResourceState<List<Product>>>
}