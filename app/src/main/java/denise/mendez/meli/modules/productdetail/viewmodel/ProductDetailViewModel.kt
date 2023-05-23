package denise.mendez.meli.modules.productdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import denise.mendez.data.network.NetworkStatus
import denise.mendez.domain.ResourceState
import denise.mendez.domain.models.ProductDetails
import denise.mendez.domain.usecases.ItemsUseCase
import denise.mendez.domain.utils.EMPTY_STRING
import denise.mendez.meli.common.BaseViewModel
import denise.mendez.meli.common.ScopedViewModel
import denise.mendez.meli.common.SingleLiveEvent
import denise.mendez.meli.modules.productdetail.entities.PictureModel
import denise.mendez.meli.modules.productdetail.entities.ProductDetailModel
import denise.mendez.meli.modules.productdetail.view.ProductDetailFragmentDirections
import denise.mendez.meli.modules.search.entities.ProductEntityList
import denise.mendez.meli.modules.search.entities.ProductItemModel
import denise.mendez.meli.utils.NavigationToDirectionEvent
import denise.mendez.meli.utils.asLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val itemsUseCase: ItemsUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _model = SingleLiveEvent<ScopedViewModel.UiModel>()
    val model get() = _model.asLiveData()

    private val _itemProduct = MutableLiveData<ProductItemModel>()
    val itemProduct get() = _itemProduct.asLiveData()

    private val _productDetail = MutableLiveData<ProductDetailModel>()
    val productDetail get() = _productDetail.asLiveData()

    private val _emptyDetail = MutableLiveData<Boolean>()
    val emptyDetail get() = _emptyDetail.asLiveData()

    lateinit var pictureModel: PictureModel

    var firstDefaultInternetCall: Boolean = true
        private set

    private lateinit var defaultList: ProductEntityList
        private set

    private lateinit var itemJob: Job

    fun init(productItem: ProductItemModel, defaultList: ProductEntityList) {
        _itemProduct.value = productItem
        getProductItemDetail(productItem.id)
        this.defaultList = defaultList
    }

    private fun getProductItemDetail(productId: String) {
        showLoaderUiModel()
        if (::itemJob.isInitialized) itemJob.cancel()
        if (productId.isNotEmpty()) {
            itemJob = viewModelScope.launch(dispatcher) {
                itemsUseCase.getItemProductDetailWithDescription(
                    productId
                ).collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is ResourceState.Success -> {
                                result.data?.let { data ->
                                    val productDetailModel = ProductDetailModel.mapFromDomain(data)
                                    showEmptyStateUiModel()
                                    _productDetail.value = productDetailModel
                                    validateDescription(data)
                                } ?: run {
                                    _model.postValue(ScopedViewModel.UiModel.EmptyState)
                                }
                            }
                            is ResourceState.Error -> {
                                showErrorUiModel()
                            }
                            is ResourceState.Loading -> {
                                showErrorUiModel()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateDescription(productDetailModel: ProductDetails) {
        if (productDetailModel.description?.plainText == EMPTY_STRING) _emptyDetail.value = true
    }

    fun showHideInternetConnection(networkStatus: NetworkStatus) {
        if (!firstDefaultInternetCall) {
            when (networkStatus) {
                NetworkStatus.Available -> showEmptyStateUiModel()
                NetworkStatus.Unavailable -> showNoInternetUiModel()
            }
        } else {
            firstDefaultInternetCall = false
        }
    }

    fun onBackPressed() {
        _navigationEvent.value = NavigationToDirectionEvent(
            ProductDetailFragmentDirections.actionProductDetailFragmentToSearchFragment(
                defaultList
            )
        )
    }

    private fun showEmptyStateUiModel() = _model.postValue(ScopedViewModel.UiModel.EmptyState)

    private fun showNoInternetUiModel() =
        _model.postValue(ScopedViewModel.UiModel.NoInternetState)

    private fun showErrorUiModel() = _model.postValue(ScopedViewModel.UiModel.ErrorState)

    private fun showLoaderUiModel() = _model.postValue(ScopedViewModel.UiModel.Loading)
}
