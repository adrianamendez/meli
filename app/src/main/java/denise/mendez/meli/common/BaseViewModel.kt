package denise.mendez.meli.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import denise.mendez.domain.utils.HTTP_ERROR
import denise.mendez.domain.utils.NETWORK_ERROR
import denise.mendez.domain.utils.TIME_OUT_ERROR
import denise.mendez.meli.R
import denise.mendez.meli.utils.NavigationEvents
import denise.mendez.meli.utils.asLiveData

open class BaseViewModel() : ViewModel() {

    private val _hasInternetConnection = SingleLiveEvent<Boolean>()
    val hasInternetConnection get() = _hasInternetConnection.asLiveData()

    protected val errorMessageSingle = SingleLiveEvent<Int>()
    val errorMessage: LiveData<Int> = errorMessageSingle

    protected val _navigationEvent = SingleLiveEvent<NavigationEvents>()
    val navigationEvent get() = _navigationEvent.asLiveData()
    fun showError(errorResponse: String?) {
        errorResponse?.let {
            errorMessageSingle.postValue(handleErrorUser(it))
        }
    }

    private fun handleErrorUser(errorMsg: String) =
        when {
            errorMsg.contains(HTTP_ERROR) -> {
                // Do something specific for HTTP errors
                R.string.http_error
            }
            errorMsg.contains(NETWORK_ERROR) -> {
                // Do something specific for network errors
                R.string.network_error
            }
            errorMsg.contains(TIME_OUT_ERROR) -> {
                // Do something specific for timeout errors
                R.string.timeout_error
            }
            else -> {
                // Do something specific for unknown errors
                R.string.generic_error
            }
        }
}
