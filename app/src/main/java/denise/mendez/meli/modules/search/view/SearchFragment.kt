package denise.mendez.meli.modules.search.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import denise.mendez.meli.adapter.GenericAdapter
import dagger.hilt.android.AndroidEntryPoint
import denise.mendez.data.network.MessageExceptionInfo
import denise.mendez.data.network.NetworkStatusHelper
import denise.mendez.meli.R
import denise.mendez.meli.common.BaseFragment
import denise.mendez.meli.components.CustomDialogFragment
import denise.mendez.meli.databinding.FragmentSearchBinding
import denise.mendez.meli.modules.search.viewmodel.SearchViewModel
import denise.mendez.meli.utils.OnSingleClickListener
import denise.mendez.meli.utils.hideKeyboard
import denise.mendez.meli.utils.viewBinding

const val TAG = "SearchFragment"

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel>(R.layout.fragment_search) {

    private val binding by viewBinding<FragmentSearchBinding>()
    override val viewModel: SearchViewModel by viewModels()
    private lateinit var navController: NavController
    private val args: SearchFragmentArgs by navArgs()
    private val productsAdapter by lazy {
        GenericAdapter { parent, _ ->
            SearchItemView(
                parent.context,
                viewModel::onItemSelected
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.apply {
            viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initViews()
        addListeners()
    }

    override fun showError(messageExceptionInfo: MessageExceptionInfo) {
        showDialog(messageExceptionInfo.title, messageExceptionInfo.message)
        binding.includeGenericErrorView.titleText.text = messageExceptionInfo.message
    }

    private fun showDialog(title: String, body: String) {
        activity?.let {
            var dialog: CustomDialogFragment? = null
            dialog = CustomDialogFragment.Builder(requireContext())
                .setIcon(R.drawable.ic_error, R.color.white)
                .setTitle(title)
                .setMessage(body)
                .setPositiveButton(R.string.accept_label, OnSingleClickListener {
                    dialog?.dismiss()
                })
                .setCancelable(false)
                .create()
            dialog.show(it.supportFragmentManager, TAG)
        }
        hideAndClear()
    }

    private fun hideAndClear() {
        binding.mainViewSearch.hideKeyboard()
        binding.mainViewSearch.clearFocus()
    }

    private fun initViews() {
        binding.rvProductsList.adapter = productsAdapter
        addSearchListener()
        context?.let { context ->
            NetworkStatusHelper(context).observe(viewLifecycleOwner, {
                viewModel.showHideInternetConnection(it)
            })
        }
        hideAndClear()
        viewModel.init(args.lastSearchedList)
    }

    private fun addSearchListener() {
        binding.productsSearchview.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchingProduct(query)
                return true
            }
        })
    }

    private fun addListeners() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            navigateFragment(it)
        })
        viewModel.notFoundSearch.observe(viewLifecycleOwner, Observer {
            if (it) showDialog(
                resources.getString(R.string.title_generic_exception_error),
                resources.getString(R.string.empty_body_not_found_label),
            )
        })
    }
}