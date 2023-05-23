package denise.mendez.meli.modules.search.view

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.example.basicworktest.denise.mendez.adapter.ItemView
import com.example.basicworktest.denise.mendez.view.properties.itemViewBinding
import denise.mendez.meli.R
import denise.mendez.meli.databinding.ItemProductBinding
import denise.mendez.meli.modules.search.entities.ProductItemModel

class SearchItemView(
    override val context: Context,
    onClickListener: ((ProductItemModel) -> Unit)
) : ItemView<ProductItemModel> {

    private val binding by  itemViewBinding<ItemProductBinding>(R.layout.item_product)

     override val view = binding.root

    override lateinit var data: ProductItemModel

    init {
        binding.root.apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
          /*  binding.cardViewTokenHome.setSingleClickListener {
                onClickListener(data)
            }*/
        }
    }

    override fun bind(item: ProductItemModel) {
        data = item
        with(binding) {
          //  this.item = item
            executePendingBindings()
        }
    }
}