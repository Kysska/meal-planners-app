package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.product.domain.Product
import com.example.ui.databinding.ProductCheckboxItemBinding

class ProductsCheckboxAdapter(
    private val onItemClickListener: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductsCheckboxAdapter.ProductViewHolder>() {

    private var products: List<Product> = emptyList()

    inner class ProductViewHolder(private val binding: ProductCheckboxItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.checkBox.text = product.name
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

    fun submitList(newProducts: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffCallback(products, newProducts))
        products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}
