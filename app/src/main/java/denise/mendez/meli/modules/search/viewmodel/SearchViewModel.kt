package denise.mendez.meli.modules.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import denise.mendez.data.network.InternetAvailability
import denise.mendez.data.network.MessageException
import denise.mendez.data.network.NetworkStatus
import denise.mendez.domain.ResourceState
import denise.mendez.domain.usecases.SitesUseCase
import denise.mendez.domain.utils.EMPTY_STRING
import denise.mendez.meli.common.BaseViewModel
import denise.mendez.meli.common.ScopedViewModel.UiModel
import denise.mendez.meli.common.SingleLiveEvent
import denise.mendez.meli.modules.search.entities.ProductEntityList
import denise.mendez.meli.modules.search.entities.ProductItemModel
import denise.mendez.meli.modules.search.view.SearchFragmentDirections
import denise.mendez.meli.utils.NavigationToDirectionEvent
import denise.mendez.meli.utils.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val PAGE_SIZE = 50

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val siteUseCase: SitesUseCase
) : BaseViewModel() {
    private val _model = SingleLiveEvent<UiModel>()
    val model get() = _model.asLiveData()
    private val _notFoundSearch = MutableLiveData<Boolean>()
    val notFoundSearch get() = _notFoundSearch.asLiveData()
    private lateinit var job: Job
    var lastQuery: String = EMPTY_STRING
        private set
    var firstDefaultInternetCall: Boolean = true
        private set
    private var defaultList = ProductEntityList(null)
        private set

    class SearchedProductItem(val productsSearchedItem: List<ProductItemModel>) :
        UiModel.FeatureModel()

    fun init(defaultList: ProductEntityList?) {
        job = viewModelScope.launch(Dispatchers.IO) {
            if (InternetAvailability.check()) showEmptyOrList()
            else showNoInternetUiModel()
        }

        defaultList?.let { this.defaultList = it }
    }

    fun searchingProduct(query: String) {
        lastQuery = query
        showLoaderUiModel()
        if (::job.isInitialized) job.cancel()
        if (query.isNotEmpty())
            job = viewModelScope.launch(Dispatchers.IO) {
                siteUseCase.getProducts(
                    query,
                    PAGE_SIZE,
                    PAGE_SIZE
                ).collect() { result ->
                    withContext(Dispatchers.Main) {
                         when (result) {
                            is ResourceState.Success -> {
                                val searchedProductList = result.data
                                if (searchedProductList.isNullOrEmpty()) {
                                    showEmptyStateUiModel()
                                    _notFoundSearch.postValue(true)
                                } else {
                                    _model.postValue(
                                        SearchedProductItem(searchedProductList.map { productEntity ->
                                            ProductItemModel.mapFromDomain(productEntity)
                                        })
                                    )
                                    defaultList = ProductEntityList(searchedProductList)
                                }

                            }
                            is ResourceState.Error -> {

                                showError(result.message)
                                showErrorUiModel()
                            }

                            is ResourceState.Loading -> {
                                showEmptyStateUiModel()
                            }
                        }

                    }
                }
            }
        else showEmptyStateUiModel()
    }


    fun onItemSelected(item: ProductItemModel) {
          _navigationEvent.value =
              NavigationToDirectionEvent(
                  SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(item, defaultList)
              )
    }

    private fun lastSearchedList() {
        _model.postValue(
            defaultList.productEntity?.let {
                SearchedProductItem(it.map { productEntity ->
                    ProductItemModel.mapFromDomain(productEntity)
                })
            }
        )
    }

    fun showHideInternetConnection(networkStatus: NetworkStatus) {
        if (!firstDefaultInternetCall)
            when (networkStatus) {
                NetworkStatus.Available -> showEmptyOrList()
                NetworkStatus.Unavailable -> showNoInternetUiModel()
            }
        else {
            firstDefaultInternetCall = false
        }
    }


    private fun showEmptyOrList() {
        if (defaultList.productEntity?.isNotEmpty() == true) {
            lastSearchedList()
        } else {
            showEmptyStateUiModel()
        }
    }

    private fun showEmptyStateUiModel() = _model.postValue(UiModel.EmptyState)

    private fun showNoInternetUiModel() = _model.postValue(UiModel.NoInternetState)

    private fun showErrorUiModel() = _model.postValue(UiModel.ErrorState)

    private fun showLoaderUiModel() = _model.postValue(UiModel.Loading)
}