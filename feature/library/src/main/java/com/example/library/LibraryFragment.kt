package com.example.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.library.databinding.FragmentLibraryBinding
import com.example.library.di.LibraryComponentProvider
import com.example.ui.adapter.CategoriesAdapter
import com.example.ui.adapter.RecipesAdapter
import com.example.ui.screens.RecipeDetailsFragment
import com.example.ui.view.ViewState
import javax.inject.Inject

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var libraryViewModel: LibraryViewModel

    private val recipesAdapter by lazy {
        RecipesAdapter { id ->
            navigateToRecipeDetails(id)
        }
    }

    private val categoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent = (requireActivity() as LibraryComponentProvider).provideHomeComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollCategories.adapter = categoriesAdapter
        binding.rvRecommendations.adapter = recipesAdapter

        observeViewModel()
    }

    private fun observeViewModel() {
        observeCategories()
        observeRecommendations()

        libraryViewModel.loadCategories()
        libraryViewModel.loadRecipes()
    }

    private fun observeCategories() {
        libraryViewModel.categoriesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    categoriesAdapter.submitList(state.data)
                }
                is ViewState.Error -> {}
            }
        }
    }

    private fun observeRecommendations() {
        libraryViewModel.recommendationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    recipesAdapter.submitList(state.data)
                }
                is ViewState.Error -> {}
            }
        }
    }

    private fun navigateToRecipeDetails(id: Int) {
        val bundle = Bundle()
        bundle.putInt(RecipeDetailsFragment.KEY_ID, id)
        findNavController().navigate(com.example.ui.R.id.recipeDetailsFragment, args = bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }
}
