package denise.mendez.domain.usecases

import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.Product
import denise.mendez.domain.repositories.SitesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SitesUseCaseImpl @Inject constructor(private val sitesRepository: SitesRepository) :
    SitesUseCase {

    override suspend fun getProducts(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<ResourceState<List<Product>>> = sitesRepository.getProducts(query, offset, limit)
}