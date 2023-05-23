package denise.mendez.meli.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

typealias AdapterFactory = (parent: ViewGroup, viewType: Int) -> ItemView<*>

open class GenericAdapter(
    private val factory: AdapterFactory
) : ListAdapter<ItemData<*>, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = factory.invoke(parent, viewType) as ItemView<Any>
        return ItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = holder as ItemViewHolder
        item.itemModel.bind(getItem(position).data!!)
    }

    override fun getItemViewType(position: Int): Int = currentList[position].type

    private class ItemViewHolder(val itemModel: ItemView<Any>) :
        RecyclerView.ViewHolder(itemModel.view)
}

class ItemDiffCallback : DiffUtil.ItemCallback<ItemData<*>>() {
    override fun areItemsTheSame(oldItem: ItemData<*>, newItem: ItemData<*>) = oldItem.data.hashCode() == newItem.data.hashCode()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ItemData<*>, newItem: ItemData<*>) = oldItem.data === newItem.data
}
