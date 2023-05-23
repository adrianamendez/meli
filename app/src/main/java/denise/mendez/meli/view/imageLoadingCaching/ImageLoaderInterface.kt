package denise.mendez.meli.view.imageLoadingCaching

import android.widget.ImageView

interface LoadImageInterface<L> {

    fun loadImage(uri: String, target: ImageView)

    fun loadImage(uri: String, target: ImageView, listener: L)
}