package com.example.weekplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeRepository
import com.example.mealtime.domain.MealtimeType
import com.example.recipe.domain.RecipeRepository
import com.example.ui.vo.RecipeView
import com.example.utils.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.Date

class MealtimeCreateViewModel(
    private val mealtimeRepository: MealtimeRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _selectedRecipe = MutableLiveData<RecipeView?>()
    val selectedRecipe: LiveData<RecipeView?> = _selectedRecipe

    private val compositeDisposable = CompositeDisposable()

    fun addCreatedMealtime(type: MealtimeType, quantity: Int, gram: Int, recipeId: Int, date: Date) {
        compositeDisposable.add(
            recipeRepository.getRecipeById(recipeId)
                .flatMapCompletable { recipe ->
                    mealtimeRepository.insertMealtime(
                        Mealtime(
                            recipe = recipe,
                            quantity = quantity,
                            gram = gram,
                            type = type,
                            date = date
                        )
                    )
                }
                .applySchedulers()
                .subscribe({}, { error ->
                    Timber.tag(MEALTIME_CREATED_VIEW_MODEL).e(error)
                })
        )
    }

    fun setSelectedRecipe(recipe: RecipeView?) {
        _selectedRecipe.value = recipe
    }

    fun removeSelectedRecipe() {
        _selectedRecipe.value = null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val MEALTIME_CREATED_VIEW_MODEL = "MealtimeCreatedViewModel"
    }
}