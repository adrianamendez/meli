package denise.mendez.meli.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import denise.mendez.data.network.MessageException
import denise.mendez.data.network.MessageExceptionInfo
import denise.mendez.meli.utils.NavigationEvents
import denise.mendez.meli.utils.asLiveData

open class BaseViewModel() : ViewModel() {

    private val _hasInternetConnection = SingleLiveEvent<Boolean>()
    val hasInternetConnection get() = _hasInternetConnection.asLiveData()

    protected val errorMessageSingle = SingleLiveEvent<MessageExceptionInfo>()
    val errorMessage: LiveData<MessageExceptionInfo> = errorMessageSingle

    protected val _navigationEvent = SingleLiveEvent<NavigationEvents>()
    val navigationEvent get() = _navigationEvent.asLiveData()

}