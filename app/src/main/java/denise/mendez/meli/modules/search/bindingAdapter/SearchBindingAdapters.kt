package denise.mendez.meli.modules.search.bindingAdapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import denise.mendez.meli.adapter.GenericAdapter
import denise.mendez.meli.adapter.ItemDataAbstract
import denise.mendez.meli.common.ScopedViewModel.UiModel
import denise.mendez.meli.modules.search.viewmodel.SearchViewModel
import denise.mendez.meli.utils.hideKeyboard

@BindingAdapter("uiLoading")
fun View.uiLoading(uiState: UiModel?) {
    visibility = if (uiState is UiModel.Loading) {
        VISIBLE
    } else {
        GONE
    }
}

@BindingAdapter("uiErrorState")
fun View.uiErrorState(uiState: UiModel?) {
    this.hideKeyboard()
    visibility = when (uiState) {
        UiModel.ErrorState -> VISIBLE
        else -> GONE
    }
}

@BindingAdapter("uiNoInternetState")
fun View.uiNoInternetState(uiState: UiModel?) {
    this.hideKeyboard()
    visibility = when (uiState) {
        UiModel.NoInternetState -> VISIBLE
        else -> GONE
    }
}

@BindingAdapter("uiEmptyState")
fun View.uiEmptyState(uiState: UiModel?) {
    this.hideKeyboard()
    visibility = when (uiState) {
        UiModel.EmptyState -> {
            VISIBLE
        }
        else -> GONE
    }
}

@BindingAdapter("uiLoadSearchedProducts")
fun RecyclerView.uiLoadSearchedProducts(uiState: UiModel?) {
    visibility = when (uiState) {
        is SearchViewModel.SearchedProductItem -> {
            val productsSearched = uiState.productsSearchedItem.toMutableList()
            (adapter as GenericAdapter).submitList(
                productsSearched.map { itemSearchedUiModel -> ItemDataAbstract(itemSearchedUiModel) }
            )
            VISIBLE
        }
        else -> GONE
    }
}

@BindingAdapter("hideIfFalse")
fun hideIfFalse(view: View, value: Boolean) {
    view.visibility = if (value) VISIBLE else GONE
}
