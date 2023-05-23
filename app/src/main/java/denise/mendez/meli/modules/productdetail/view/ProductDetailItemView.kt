package denise.mendez.meli.modules.productdetail.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import denise.mendez.meli.adapter.ItemView
import denise.mendez.meli.databinding.ViewPicturesBinding
import denise.mendez.meli.modules.productdetail.entities.PictureModel

class ProductDetailItemView(
    override val context: Context
) : ItemView<PictureModel> {

    private val binding = ViewPicturesBinding.inflate(LayoutInflater.from(context))

    override val view = binding.root

    override lateinit var data: PictureModel

    init {
        binding.root.apply {
            layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT
            )
        }
    }

    override fun bind(item: PictureModel) {
        data = item
        binding.model = item
        binding.executePendingBindings()
    }
}
