package com.example.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.ui.view.ViewState
import com.example.utils.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _recommendationState = MutableLiveData<ViewState<List<Recipe>>>()
    val recommendationState: LiveData<ViewState<List<Recipe>>>
        get() = _recommendationState

    private val compositeDisposable = CompositeDisposable()

    fun loadRecipes() {
        _recommendationState.value = ViewState.Loading
        compositeDisposable.add(
            recipeRepository.getRecipes(LIMIT_RECOMMENDATION)
                .applySchedulers()
                .subscribe({ recipes ->
                    _recommendationState.value = if (recipes.isNotEmpty()) {
                        ViewState.Success(recipes)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _recommendationState.value = ViewState.Error(error)
                })
        )
    }

    fun loadRecipesByCategory(id: Int) {
        _recommendationState.value = ViewState.Loading
        compositeDisposable.add(
            recipeRepository.getRecipeByCategory(id)
                .applySchedulers()
                .subscribe({ recipes ->
                    _recommendationState.value = if (recipes.isNotEmpty()) {
                        ViewState.Success(recipes)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _recommendationState.value = ViewState.Error(error)
                })
        )
    }

    fun loadRecipesByQuery(query: String) {
        _recommendationState.value = ViewState.Loading
        compositeDisposable.add(
            recipeRepository.getRecipeByQuery(query)
                .applySchedulers()
                .subscribe({ recipes ->
                    _recommendationState.value = if (recipes.isNotEmpty()) {
                        ViewState.Success(recipes)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _recommendationState.value = ViewState.Error(error)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val LIMIT_RECOMMENDATION = 10
    }
}
