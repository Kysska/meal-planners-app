package com.example.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipe.domain.Recipe
import com.example.search.databinding.FragmentSearchBinding
import com.example.search.di.SearchComponentProvider
import com.example.ui.adapter.RecipesAdapter
import com.example.ui.screens.RecipeDetailsFragment
import com.example.ui.utils.SearchMode
import com.example.ui.utils.SearchTypeData
import com.example.ui.view.SearchBar
import com.example.ui.view.ViewState
import com.example.ui.vo.RecipeView
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search), SearchBar.OnSearchActionListener, SearchBar.OnClearSearchClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private val recipesAdapter by lazy {
        RecipesAdapter(onDetailClickListener = { id -> navigateToRecipeDetails(id) }, onRecipeClickListener = { recipe -> sendRecipe(recipe) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent =
            (requireActivity() as SearchComponentProvider).provideSearchComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchBar.setOnClearButtonClickListener(this)
        binding.searchBar.setOnSearchActionListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = getTitleArgument()
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            rvRecommendations.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            rvRecommendations.adapter = recipesAdapter

            when (title) {
                SearchTypeData.CATEGORY -> {
                    val id = getIdCategory()
                    searchViewModel.loadRecipesByCategory(id)
                }

                SearchTypeData.RECOMMENDATION -> {
                    tvRecommendation.text =
                        requireContext().getString(com.example.ui.R.string.library_recommendation_title)
                    searchViewModel.loadRecipes()
                }

                SearchTypeData.SEARCH -> {
                    tvRecommendation.text =
                        requireContext().getString(com.example.ui.R.string.library_search_title)
                    val searchText = getSearch()
                    if (searchText.isNotEmpty()) {
                        binding.searchBar.setText(searchText)
                        searchViewModel.loadRecipesByQuery(searchText)
                    } else {
                        searchViewModel.loadRecipes()
                    }
                }
            }
        }

        observeViewModel(title)
    }

    private fun observeViewModel(title: SearchTypeData) {
        searchViewModel.recommendationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    if (state.data.isNotEmpty()) {
                        recipesAdapter.submitList(state.data)
                        if (title == SearchTypeData.CATEGORY) {
                            binding.tvRecommendation.text = state.data.first().category.name
                        }
                    }
                }
                is ViewState.Error -> {}
                else -> {}
            }
        }
    }

    private fun sendRecipe(recipe: Recipe) {
        if (getSearchMode() == SearchMode.EDIT) {
            val bundle = Bundle()
            bundle.putParcelable(KEY_RECIPE_VIEW, RecipeView(id = recipe.id, name = recipe.name, image = recipe.image, times = recipe.times))
            parentFragmentManager.setFragmentResult(KEY_RECIPE_SELECTION, bundle)
            findNavController().navigateUp()
        }
    }

    private fun getTitleArgument(): SearchTypeData {
        val title = requireArguments().getString(KEY_STRING, SearchTypeData.RECOMMENDATION.name)
        return SearchTypeData.valueOf(title)
    }

    private fun getIdCategory(): Int {
        return requireArguments().getInt(KEY_ID_CATEGORY)
    }

    private fun getSearchMode(): SearchMode {
        val searchMode = requireArguments().getString(KEY_SEARCH_MODE, SearchMode.VIEW_ONLY.name)
        return SearchMode.valueOf(searchMode)
    }

    private fun getSearch(): String {
        return requireArguments().getString(KEY_SEARCH) ?: ""
    }

    private fun navigateToRecipeDetails(id: Int) {
        val bundle = Bundle()
        bundle.putInt(RecipeDetailsFragment.KEY_ID, id)
        findNavController().navigate(com.example.ui.R.id.recipeDetailsFragment, args = bundle)
    }

    override fun onClearButtonClicked() {
        binding.searchBar.clear()
    }

    override fun onSearchSubmitted(query: String) {
        if (query.isNotEmpty()) {
            searchViewModel.loadRecipesByQuery(query)
        } else {
            searchViewModel.loadRecipes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        binding.searchBar.clear()
    }

    companion object {
        const val KEY_STRING = "title"
        const val KEY_SEARCH_MODE = "searchMode"
        const val KEY_RECIPE_VIEW = "recipeView"
        const val KEY_RECIPE_SELECTION = "recipeSelection"
        const val KEY_ID_CATEGORY = "id"
        const val KEY_SEARCH = "search"
        private const val SPAN_COUNT = 2
    }
}
