package denise.mendez.meli.modules.productdetail.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.basicworktest.denise.mendez.utils.CubeInDepthTransformer
import denise.mendez.domain.models.Product.ItemStatus.NEW
import denise.mendez.domain.models.Product.ItemStatus.USED
import denise.mendez.meli.R
import denise.mendez.meli.adapter.GenericAdapter
import denise.mendez.meli.adapter.ItemDataAbstract
import denise.mendez.meli.modules.productdetail.entities.PictureModel
import denise.mendez.meli.modules.search.entities.ProductItemModel
import denise.mendez.meli.utils.getScreenWidth

@BindingAdapter("itemCondition")
fun TextView.itemCondition(product: ProductItemModel?) {
    product?.let {
        val condition = when (it.condition) {
            NEW.name -> context.getString(R.string.condition_new_label)
            USED.name -> context.getString(R.string.condition_used_label)
            else -> context.getString(R.string.condition_not_specified_label)
        }
        text = context.getString(
            R.string.condition_formatted,
            condition,
            it.soldQuantity
        )
    }
}

@BindingAdapter("loadViewPagerDetail")
fun ViewPager2.loadContent(list: List<PictureModel>?) {
    if (!list.isNullOrEmpty()) {
        apply {
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).apply {
                context.getScreenWidth().div(4).let {
                    setPadding(it, 0, it, 0)
                    clipToPadding = false
                }
            }
            (adapter as GenericAdapter).submitList(
                list.map { model -> ItemDataAbstract(model) }
            )
            setPageTransformer(CubeInDepthTransformer())
            setCurrentItem(0, true)
        }
    }
}
