package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.domain.Recipe
import com.example.ui.databinding.RecipeItemBinding
import com.example.ui.extensions.loadImage

class RecipesAdapter(private val onClickListener: (id: Int) -> Unit) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = emptyList()

    inner class RecipeViewHolder(private val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.tvTitle.text = recipe.name
            binding.tvRating.text = recipe.times
            binding.imageFood.loadImage(recipe.image)

            binding.tvRecipeCount.setOnClickListener {
                onClickListener(recipe.id)
            }
        }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    fun submitList(newRecipes: List<Recipe>) {
        val diffResult = DiffUtil.calculateDiff(RecipeDiffCallback(recipes, newRecipes))
        recipes = newRecipes
        diffResult.dispatchUpdatesTo(this)
    }
}

class RecipeDiffCallback(
    private val oldList: List<Recipe>,
    private val newList: List<Recipe>
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
