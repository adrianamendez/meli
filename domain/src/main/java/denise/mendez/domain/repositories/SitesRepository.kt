package denise.mendez.domain.repositories

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface SitesRepository {
    suspend fun getProducts(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<ResourceState<List<Product>>>
}