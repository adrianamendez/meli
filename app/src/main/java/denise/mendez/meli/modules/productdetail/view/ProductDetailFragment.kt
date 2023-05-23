package denise.mendez.meli.modules.productdetail.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import denise.mendez.meli.adapter.GenericAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import denise.mendez.data.network.MessageExceptionInfo
import denise.mendez.data.network.NetworkStatusHelper
import denise.mendez.meli.R
import denise.mendez.meli.common.BaseFragment
import denise.mendez.meli.components.CustomDialogFragment
import denise.mendez.meli.databinding.FragmentProductDetailBinding
import denise.mendez.meli.modules.productdetail.viewmodel.ProductDetailViewModel
import denise.mendez.meli.modules.search.view.TAG
import denise.mendez.meli.utils.OnSingleClickListener
import denise.mendez.meli.utils.setSingleClickListener
import denise.mendez.meli.utils.shareImageIntent
import denise.mendez.meli.utils.viewBinding

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<ProductDetailViewModel>(R.layout.fragment_product_detail) {

    private val binding by viewBinding<FragmentProductDetailBinding>()
    override val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var navController: NavController
    private val args: ProductDetailFragmentArgs by navArgs()
    private val genericAdapter by lazy {
        GenericAdapter { parent, _ -> ProductDetailItemView(parent.context) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.apply {
            viewModel = this@ProductDetailFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel.init(args.itemSearched, args.lastSearchedList)
        initViews()
        initToolbar()
    }

    override fun showError(messageExceptionInfo: MessageExceptionInfo) {
        showDialog(messageExceptionInfo.title, messageExceptionInfo.message)
        binding.includeGenericErrorView.titleText.text = messageExceptionInfo.message
    }

    private fun initViews() {
        context?.let { context ->
            NetworkStatusHelper(context).observe(viewLifecycleOwner, {
                viewModel.showHideInternetConnection(it)
            })
        }
        setAdapter()
        binding.shareImageview.setSingleClickListener {
            addSingleClickShare()
        }
        binding.shareTextview.setSingleClickListener {
            addSingleClickShare()
        }
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            navigateFragment(it)
        })
    }

    private fun addSingleClickShare() {
        viewModel.productDetail.value?.permalink?.let { url ->
            this.shareImageIntent(
                url
            )
        }
    }

    private fun initToolbar() {
        binding.toolbar.setSingleClickListener {
            viewModel.onBackPressed()
        }
    }

    private fun setAdapter() {
        binding.viewPagerProductDetail.adapter = genericAdapter
        binding.viewPagerProductDetail.adapter?.let {
            TabLayoutMediator(
                binding.tabIndicator,
                binding.viewPagerProductDetail
            ) { tab, position -> }.attach()
        }
        viewModel.emptyDetail.observe(viewLifecycleOwner, Observer {
            if (it) showDialog(
                resources.getString(R.string.title_generic_exception_error),
                resources.getString(R.string.empty_description_body_label),
            )
        })
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
    }
}
