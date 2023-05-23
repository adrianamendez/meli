package denise.mendez.meli.modules.productdetail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import denise.mendez.domain.ResourceState
import denise.mendez.domain.usecases.ItemsUseCase
import denise.mendez.meli.common.ScopedViewModel
import denise.mendez.meli.modules.CoroutinesTestRule
import denise.mendez.meli.modules.ProductDetailsMockk
import denise.mendez.meli.modules.callPrivateFun
import denise.mendez.meli.modules.mockedProductEntityList
import denise.mendez.meli.modules.mockedProductItemModel
import denise.mendez.meli.modules.productdetail.view.ProductDetailFragmentDirections
import denise.mendez.meli.utils.NavigationToDirectionEvent
import junit.framework.TestCase.assertEquals
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
class ProductDetailViewModelTest {

    @Mock
    lateinit var itemsUseCase: ItemsUseCase

    private val observer: Observer<ScopedViewModel.UiModel> = mock()

    private lateinit var sut: ProductDetailViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        const val PRODUCT_ID = "MCO559474248"
    }

    @Before
    fun setup() {
        sut = ProductDetailViewModel(itemsUseCase, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun searchProduct_query_returnSuccess() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(itemsUseCase.getItemProductDetailWithDescription(PRODUCT_ID)).willReturn(
                flowOf(ResourceState.Success(ProductDetailsMockk))
            )

            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductItemDetail", PRODUCT_ID)
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
            given(itemsUseCase.getItemProductDetailWithDescription(PRODUCT_ID)).willReturn(
                flowOf(ResourceState.Error(message = "error"))
            )

            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductItemDetail", PRODUCT_ID)
            // then
            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    ScopedViewModel.UiModel.ErrorState
                )
            )
        }

    @Test
    fun onBackPressed_event_triggered() {
        // given
        sut.init(mockedProductItemModel, mockedProductEntityList)
        val event = NavigationToDirectionEvent(
            ProductDetailFragmentDirections.actionProductDetailFragmentToSearchFragment(
                mockedProductEntityList
            )
        )
        // when
        sut.onBackPressed()
        // then
        assertEquals(event, sut.navigationEvent.value)
    }
}
