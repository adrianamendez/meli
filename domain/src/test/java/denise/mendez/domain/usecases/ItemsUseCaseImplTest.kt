package denise.mendez.domain.usecases

import androidx.test.filters.SmallTest
import denise.mendez.domain.ResourceState
import denise.mendez.domain.repositories.ItemsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
@SmallTest
class ItemsUseCaseImplTest {

    private val itemsRepository: ItemsRepository = mock(ItemsRepository::class.java)
    private val useCase = ItemsUseCaseImpl(itemsRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getItemDescription should return the same flow as repository`() = runBlocking {
        // given

        val repositoryFlow = flowOf(ResourceState.Success(itemDescriptionMockk))
        `when`(itemsRepository.getItemDescription(idProductMockk)).thenReturn(repositoryFlow)

        // when
        val useCaseFlow = useCase.getItemDescription(idProductMockk)

        // then
        assertEquals(repositoryFlow.first().data, useCaseFlow.first().data)
    }

    @Test
    fun `getItemProductDetail should return the same flow as repository`() = runBlocking {
        // given

        val repositoryFlow = flowOf(ResourceState.Success(ProductDetailsMockk))
        `when`(itemsRepository.getItemProductDetail(idProductMockk)).thenReturn(repositoryFlow)

        // when
        val useCaseFlow = useCase.getItemProductDetail(idProductMockk)

        // then
        assertEquals(repositoryFlow.first().data, useCaseFlow.first().data)
    }

    @Test
    fun `getItemProductDetailWithDescription should return the same flow as repository`() = runBlocking {
        // given

        val repositoryFlow = flowOf(ResourceState.Success(ProductDetailsMockk))
        `when`(itemsRepository.getItemProductDetailWithDescription(idProductMockk)).thenReturn(repositoryFlow)

        // when
        val useCaseFlow = useCase.getItemProductDetailWithDescription(idProductMockk)

        // then
        assertEquals(repositoryFlow.first().data, useCaseFlow.first().data)
    }
}

