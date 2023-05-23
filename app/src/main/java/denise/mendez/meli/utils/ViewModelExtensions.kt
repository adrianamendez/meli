package denise.mendez.meli.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.viewModel(
    noinline factory: (() -> T)? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val viewModelProviderFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory?.invoke() as? T ?: modelClass.newInstance()
            }
        }
        val viewModelProvider = ViewModelProvider(this, viewModelProviderFactory)
        viewModelProvider.get(T::class.java)
    }
}
