package denise.mendez.meli.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

abstract class ScopedViewModel : ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    sealed class UiModel {
        object Loading : UiModel()
        object EmptyState : UiModel()
        object ErrorState : UiModel()
        object NoInternetState : UiModel()
        abstract class FeatureModel : UiModel()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}