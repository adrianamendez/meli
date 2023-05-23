package denise.mendez.meli.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import denise.mendez.domain.utils.EMPTY_STRING
import denise.mendez.domain.utils.SHARING_URL
import denise.mendez.domain.utils.TEXT_PLAIN_FORMAT
import java.text.NumberFormat
import java.util.Locale


fun View.hideKeyboard() {
    context.hideKeyboard(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.visibleOrGone(visible: Boolean) {
    val map: Map<Boolean, Int> = mapOf(true to View.VISIBLE, false to View.GONE)
    map[visible]?.let { this.visibility = it }
}

fun ImageView.loadImage(url: String) = Glide.with(this).load(url).into(this)

fun ContentLoadingProgressBar.shouldShow(value: Boolean) {
    if (value) show() else hide()
}

fun Double.moneyFormat(): String {
    val nf = NumberFormat.getNumberInstance(Locale.GERMAN)
    nf.isGroupingUsed = true
    return "$ ${nf.format(this)}"
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun View.setSingleClickListener(action: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(action))
}

fun Context.getScreenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Fragment.shareImageIntent(uri: String) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_SUBJECT, SHARING_URL)
        putExtra(Intent.EXTRA_TEXT, uri)
        type = TEXT_PLAIN_FORMAT
        startActivity(Intent.createChooser(this, EMPTY_STRING))
    }
}