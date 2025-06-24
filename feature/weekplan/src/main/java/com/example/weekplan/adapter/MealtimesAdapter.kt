package com.example.weekplan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeType
import com.example.ui.databinding.HeaderMealtimeItemBinding
import com.example.ui.databinding.MealtimeItemBinding
import com.example.ui.extensions.loadImage
import com.example.weekplan.R

class MealtimesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<MealtimeListItem> = emptyList()

    inner class MealtimeViewHolder(private val binding: MealtimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mealtime: Mealtime) {
            binding.mealImage.loadImage(mealtime.recipe.image)
            binding.itemMealName.text = mealtime.recipe.name
            binding.itemMealQuantity.text = binding.root.context.getString(
                R.string.mealtime_quantity,
                mealtime.quantity.toString(),
                mealtime.gram.toString()
            )

            binding.editIcon.setOnClickListener {
            }
        }
    }

    inner class HeadersViewHolder(private val binding: HeaderMealtimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(type: MealtimeType) {
            binding.title.text = when (type) {
                MealtimeType.BREAKFAST -> "Завтрак"
                MealtimeType.LUNCH -> "Обед"
                MealtimeType.DINNER -> "Ужин"
                MealtimeType.SNACKS -> "Перекус"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MealtimeListItem.Header -> HEADER_TYPE
            is MealtimeListItem.MealtimeItem -> MEAL_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeadersViewHolder -> holder.bind((items[position] as MealtimeListItem.Header).type)
            is MealtimeViewHolder -> holder.bind((items[position] as MealtimeListItem.MealtimeItem).mealtime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> HeadersViewHolder(
                HeaderMealtimeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            MEAL_TYPE -> MealtimeViewHolder(
                MealtimeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<MealtimeListItem>) {
        val diffResult = DiffUtil.calculateDiff(MealtimeListItemDiffCallback(items, list))
        items = list
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        const val HEADER_TYPE = 0
        const val MEAL_TYPE = 1
    }
}

class MealtimeListItemDiffCallback(
    private val oldList: List<MealtimeListItem>,
    private val newList: List<MealtimeListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is MealtimeListItem.Header && newItem is MealtimeListItem.Header ->
                oldItem.type.name == newItem.type.name

            oldItem is MealtimeListItem.MealtimeItem && newItem is MealtimeListItem.MealtimeItem ->
                oldItem.mealtime.id == newItem.mealtime.id

            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is MealtimeListItem.Header && newItem is MealtimeListItem.Header ->
                oldItem.type.name == newItem.type.name

            oldItem is MealtimeListItem.MealtimeItem && newItem is MealtimeListItem.MealtimeItem ->
                oldItem.mealtime == newItem.mealtime

            else -> false
        }
    }
}
