package denise.mendez.meli.modules.search.bindingAdapter

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import denise.mendez.domain.utils.EMPTY_STRING
import denise.mendez.meli.R
import denise.mendez.meli.utils.moneyFormat
import denise.mendez.meli.view.imageLoadingCaching.ImageLoader

@BindingAdapter("loadImageUrl")
fun ImageView.loadImage(url: String?) {
    url?.let { ImageLoader(context).loadImage(url, this) }
}

@BindingAdapter("hasFreeShipping")
fun TextView.hasFreeShipping(freeShipping: Boolean) {
    if (freeShipping) {
        this.text = context.resources.getString(R.string.free_shipping)
        this.visibility = VISIBLE
    } else this.visibility = INVISIBLE

}

@BindingAdapter("hasShippingGuaranteed")
fun TextView.hasShippingGuaranteed(hasShippingGuaranteed: Boolean) {
    if (hasShippingGuaranteed) {
        this.text = context.resources.getString(R.string.guaranteed_shipping)
        this.visibility = VISIBLE
    } else this.visibility = INVISIBLE
}

@BindingAdapter("fulFillmentLabel")
fun TextView.fulFillmentLabel(fulFillmentLabel: Boolean) {
    this.text = if (fulFillmentLabel) context.resources.getString(R.string.full) else EMPTY_STRING
}

@BindingAdapter("setPrice")
fun TextView.setPrice(price: Double) {
    this.text = price.moneyFormat()
}