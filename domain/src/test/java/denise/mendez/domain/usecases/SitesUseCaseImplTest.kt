package denise.mendez.domain.usecases

import androidx.test.filters.SmallTest
import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.Product
import denise.mendez.domain.repositories.SitesRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class SitesUseCaseImplTest {

    private val sitesRepository: SitesRepository = mockk()
    private val useCase = SitesUseCaseImpl(sitesRepository)

    @Test
    fun `getProducts should return the same flow as repository`() = runBlocking {
        // given
        val query = "product"
        val offset = 0
        val limit = 10

        val repositoryFlow = flow<ResourceState<List<Product>>> { emit(ResourceState.Success(listOf(productMockk))) }
        coEvery { sitesRepository.getProducts(query, offset, limit) } returns repositoryFlow

        // when
        val useCaseFlow = useCase.getProducts(query, offset, limit)

        // then
        assertEquals(repositoryFlow.first().data, useCaseFlow.first().data)
    }
}
