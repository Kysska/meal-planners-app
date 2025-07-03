package com.example.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.databinding.FragmentLibraryBinding
import com.example.library.di.LibraryComponentProvider
import com.example.search.SearchFragment
import com.example.search.SearchFragment.Companion.KEY_SEARCH
import com.example.ui.adapter.CategoriesAdapter
import com.example.ui.adapter.RecipesAdapter
import com.example.ui.screens.RecipeDetailsFragment
import com.example.ui.utils.SearchTypeData
import com.example.ui.view.SearchBar
import com.example.ui.view.ViewState
import javax.inject.Inject

class LibraryFragment : Fragment(R.layout.fragment_library), SearchBar.OnSearchActionListener, SearchBar.OnClearSearchClickListener {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var libraryViewModel: LibraryViewModel

    private val recipesAdapter by lazy {
        RecipesAdapter(onDetailClickListener = { id ->
            navigateToRecipeDetails(id)
        })
    }

    private val categoriesAdapter by lazy {
        CategoriesAdapter { id ->
            navigateToSearchFragmentCategoryType(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent =
            (requireActivity() as LibraryComponentProvider).provideHomeComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        binding.searchBar.setOnSearchActionListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollCategories.adapter = categoriesAdapter
        binding.rvRecommendations.adapter = recipesAdapter
        binding.rvRecommendations.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.tvSeeAllRecommendation.setOnClickListener {
            navigateToSearchFragmentRecommendationType()
        }

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

    private fun navigateToSearchFragmentCategoryType(id: Int = 0) {
        val bundle = Bundle()
        bundle.putString(SearchFragment.KEY_STRING, SearchTypeData.CATEGORY.name)
        bundle.putInt(SearchFragment.KEY_ID_CATEGORY, id)
        findNavController().navigate(com.example.ui.R.id.searchFragment, args = bundle)
    }

    private fun navigateToSearchFragmentRecommendationType() {
        val bundle = Bundle()
        bundle.putString(SearchFragment.KEY_STRING, SearchTypeData.RECOMMENDATION.name)
        findNavController().navigate(com.example.ui.R.id.searchFragment, args = bundle)
    }

    private fun navigateToSearchFragmentSearchType(query: String) {
        val bundle = Bundle()
        bundle.putString(SearchFragment.KEY_STRING, SearchTypeData.SEARCH.name)
        bundle.putString(SearchFragment.KEY_SEARCH, query)
        findNavController().navigate(com.example.ui.R.id.searchFragment, args = bundle)
    }

    override fun onSearchSubmitted(query: String) {
        navigateToSearchFragmentSearchType(query)
    }

    override fun onClearButtonClicked() {
        binding.searchBar.clear()
    }

    override fun onStop() {
        super.onStop()
        binding.searchBar.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
