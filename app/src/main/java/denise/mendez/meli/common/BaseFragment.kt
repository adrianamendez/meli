package denise.mendez.meli.common

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import denise.mendez.meli.utils.FinishResultEvent
import denise.mendez.meli.utils.NavigationEvents
import denise.mendez.meli.utils.NavigationToActionIdEvent
import denise.mendez.meli.utils.NavigationToDirectionEvent
import denise.mendez.meli.utils.NavigationUpEvent
import denise.mendez.meli.utils.OnBackPressedEvent
import denise.mendez.meli.utils.PopBackStackEvent
import denise.mendez.meli.utils.StartActivityEvent

abstract class BaseFragment<ViewModel : BaseViewModel>(layout: Int) :
    Fragment(layout),
    ErrorView {
    protected abstract val viewModel: ViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupErrorMessage()
    }

    private fun setupErrorMessage() {
        viewModel.errorMessage.observe(
            viewLifecycleOwner
        ) {
            showError(it)
        }
    }

    /**
     * Observer Events
     */

    protected fun <T : BaseViewModel> observerViewModelEvents(viewModel: T) {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer(::navigateFragment))
    }

    protected fun <T : BaseItemViewManager> observerViewManagerEvents(viewManagers: List<T>) {
        viewManagers.forEach {
            it.navigationEvent.observe(viewLifecycleOwner, Observer(::navigateFragment))
        }
    }

    /**
     * Actions
     */

    protected fun navigateFragment(navigation: NavigationEvents) {
        when (navigation) {
            is StartActivityEvent -> startActivity(navigation)
            is FinishResultEvent -> finishActivity(navigation)
            is NavigationToDirectionEvent -> findNavController().navigate(navigation.navDirections)
            is NavigationToActionIdEvent -> findNavController().navigate(navigation.actionId, navigation.arguments)
            is NavigationUpEvent -> findNavController().navigateUp()
            is PopBackStackEvent -> findNavController().popBackStack()
            is OnBackPressedEvent -> onBackPressed(navigation)
        }
    }

    protected fun onBackPressed(onBackPressedEvent: OnBackPressedEvent) {
        requireActivity().setResult(onBackPressedEvent.resultCode)
        requireActivity().onBackPressed()
    }

    protected fun startActivity(startActivityEvent: StartActivityEvent) {
        val intent = Intent(context, startActivityEvent.activity).apply {
            startActivityEvent.bundle?.let { putExtras(it) }
            flags = startActivityEvent.flag
        }
        startActivityForResult(intent, startActivityEvent.code)
    }

    protected fun finishActivity(finishResultEvent: FinishResultEvent) {
        activity?.let { activity ->
            with(finishResultEvent) {
                bundle?.let { activity.setResult(resultCode, Intent().apply { putExtras(it) }) }
                    ?: activity.setResult(resultCode)
            }
            activity.finish()
        }
    }
}
