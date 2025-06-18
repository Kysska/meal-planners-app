package com.example.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.ui.view.ViewState
import com.example.utils.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable

class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _recommendationState = MutableLiveData<ViewState<Recipe>>()
    val recommendationState: LiveData<ViewState<Recipe>>
        get() = _recommendationState

    private val compositeDisposable = CompositeDisposable()

    fun loadRecipe(id: Int) {
        if (_recommendationState.value is ViewState.Success) return

        _recommendationState.value = ViewState.Loading
        compositeDisposable.add(
            recipeRepository.getRecipeById(id)
                .applySchedulers()
                .subscribe({ recipes ->
                    _recommendationState.value = ViewState.Success(recipes)
                }, { error ->
                    _recommendationState.value = ViewState.Error(error)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
