package com.example.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.category.domain.Category
import com.example.category.domain.CategoryRepository
import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.ui.view.ViewState
import com.example.utils.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable

class LibraryViewModel(
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _categoriesState = MutableLiveData<ViewState<List<Category>>>()
    val categoriesState: LiveData<ViewState<List<Category>>>
        get() = _categoriesState

    private val _recommendationState = MutableLiveData<ViewState<List<Recipe>>>()
    val recommendationState: LiveData<ViewState<List<Recipe>>>
        get() = _recommendationState

    private val compositeDisposable = CompositeDisposable()

    fun loadCategories() {
        if (_categoriesState.value is ViewState.Success) return

        _categoriesState.value = ViewState.Loading
        compositeDisposable.add(
            categoryRepository.getCategories()
                .applySchedulers()
                .subscribe({ categories ->
                    _categoriesState.value = if (categories.isNotEmpty()) {
                        ViewState.Success(categories)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _categoriesState.value = ViewState.Error(error)
                })
        )
    }

    fun loadRecipes() {
        if (_recommendationState.value is ViewState.Success) return

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val LIMIT_RECOMMENDATION = 10
    }
}
