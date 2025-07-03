package com.example.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.product.domain.Product
import com.example.ui.R
import com.example.ui.adapter.ProductsAdapter
import com.example.ui.databinding.FragmentRecipeDetailsBinding
import com.example.ui.di.CommonComponentProvider
import com.example.ui.extensions.loadImage
import com.example.ui.view.ViewState
import com.example.ui.viewModel.RecipeDetailViewModel
import javax.inject.Inject

class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val productsAdapter by lazy {
        ProductsAdapter {}
    }

    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent = (requireActivity() as CommonComponentProvider).provideCommonComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = getIdArgument()
        observeViewModel(id)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            ingredientsList.adapter = productsAdapter
        }
    }

    private fun getIdArgument(): Int {
        return requireArguments().getInt(KEY_ID)
    }

    private fun observeViewModel(id: Int) {
        recipeDetailViewModel.recommendationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    binding.apply {
                        state.data.apply {
                            dishName.text = name
                            dishImage.loadImage(image)
                            tvCalories.text = nutrients.kcal.toString()
                            tvProteins.text = nutrients.protein.toString()
                            tvCarbs.text = nutrients.carbs.toString()
                            tvFats.text = nutrients.fats.toString()
                            tvTimes.text = times
                            tvDifficult.text = difficult
                            val result = context?.getString(R.string.description, description, steps)
                            tvDescription.text = result
                            productsAdapter.submitList(products)
                            setOnClickListener(products)
                        }
                    }
                }
                is ViewState.Error -> {}
            }
        }
        recipeDetailViewModel.loadRecipe(id)
    }

    private fun setOnClickListener(products: List<Product>) {
        binding.exportRecipeDetailsButton.setOnClickListener {
            recipeDetailViewModel.exportProducts(products)
            binding.exportRecipeDetailsButton.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_ID = "id"
    }
}
