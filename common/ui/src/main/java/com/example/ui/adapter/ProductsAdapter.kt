package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.product.domain.Product
import com.example.ui.databinding.ProductItemBinding
import com.example.ui.extensions.loadImage

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var products: List<Product> = emptyList()

    inner class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.itemMealName.text = product.name
            binding.mealImage.loadImage(product.image)
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    fun submitList(newProducts: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffCallback(products, newProducts))
        products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}

class ProductDiffCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
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
