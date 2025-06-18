package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.category.domain.Category
import com.example.ui.databinding.CategoriesItemBinding

class CategoriesAdapter(private val onClickListener: (id: Int) -> Unit) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = emptyList()

    inner class CategoryViewHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.title.text = category.name

            binding.cardItem.setOnClickListener {
                onClickListener(category.id)
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    fun submitList(newCategories: List<Category>) {
        val diffResult = DiffUtil.calculateDiff(CategoriesDiffCallback(categories, newCategories))
        categories = newCategories
        diffResult.dispatchUpdatesTo(this)
    }
}

class CategoriesDiffCallback(
    private val oldList: List<Category>,
    private val newList: List<Category>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
