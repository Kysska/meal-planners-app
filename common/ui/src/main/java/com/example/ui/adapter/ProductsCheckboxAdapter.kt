package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.product.domain.ProductInCart
import com.example.ui.databinding.ProductCheckboxItemBinding

class ProductsCheckboxAdapter(
    private val onItemClickListener: (product: ProductInCart) -> Unit
) : RecyclerView.Adapter<ProductsCheckboxAdapter.ProductViewHolder>() {

    private var products: List<ProductInCart> = emptyList()

    inner class ProductViewHolder(private val binding: ProductCheckboxItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductInCart) {
            binding.checkBox.text = product.product.name
            binding.checkBox.isChecked = product.selected
            binding.checkBox.setOnClickListener {
                onItemClickListener(product)
            }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductCheckboxItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    fun submitList(newProducts: List<ProductInCart>) {
        val diffResult = DiffUtil.calculateDiff(ProductInCartDiffCallback(products, newProducts))
        products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}

class ProductInCartDiffCallback(
    private val oldList: List<ProductInCart>,
    private val newList: List<ProductInCart>
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
