package denise.mendez.meli.common

import denise.mendez.meli.utils.NavigationEvents
import denise.mendez.meli.utils.asLiveData

abstract class BaseItemViewManager {

    private val _navigationEvent = SingleLiveEvent<NavigationEvents>()
    val navigationEvent get() = _navigationEvent.asLiveData()
}
