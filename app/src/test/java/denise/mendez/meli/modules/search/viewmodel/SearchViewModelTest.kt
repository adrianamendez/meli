package denise.mendez.meli.modules.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import denise.mendez.domain.ResourceState
import denise.mendez.domain.usecases.SitesUseCase
import denise.mendez.meli.common.ScopedViewModel
import denise.mendez.meli.modules.CoroutinesTestRule
import denise.mendez.meli.modules.productListMockk
import denise.mendez.meli.modules.search.entities.ProductItemModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Mock
    lateinit var siteUseCase: SitesUseCase

    private val observer: Observer<ScopedViewModel.UiModel> = mock()

    private lateinit var sut: SearchViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        const val query = "moto"
        const val offset = 50
    }

    @Before
    fun setup() {
        sut = SearchViewModel(siteUseCase, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `searching product with empty query return empty state`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            sut.model.observeForever(observer)
            // when
            sut.searchingProduct("")
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun searchProduct_EmptyQuery_returnEmptyState() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            sut.model.observeForever(observer)
            // when
            sut.searchingProduct("")
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun searchProduct_query_returnSuccess() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(siteUseCase.getProducts(query, offset, offset)).willReturn(
                flowOf(ResourceState.Success(productListMockk))
            )

            sut.model.observeForever(observer)
            // when
            sut.searchingProduct(query)
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    SearchViewModel.SearchedProductItem(
                        productListMockk.map { productEntity ->
                            ProductItemModel.mapFromDomain(productEntity)
                        }
                    )
                )
            )
        }

    @Test
    fun searchProduct_query_returnSuccessEmpty() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(siteUseCase.getProducts(query, offset, offset)).willReturn(
                flowOf(ResourceState.Success(emptyList()))
            )

            sut.model.observeForever(observer)
            // when
            sut.searchingProduct(query)
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun searchProduct_query_returnError() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(siteUseCase.getProducts(query, offset, offset)).willReturn(
                flowOf(ResourceState.Error(message = "error"))
            )

            sut.model.observeForever(observer)
            // when
            sut.searchingProduct(query)
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    ScopedViewModel.UiModel.ErrorState
                )
            )
        }
}
