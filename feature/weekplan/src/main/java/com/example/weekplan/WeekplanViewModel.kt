package com.example.weekplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeRepository
import com.example.mealtime.domain.MealtimeType
import com.example.recipe.domain.Recipe
import com.example.ui.view.ViewState
import com.example.utils.util.applySchedulers
import com.example.weekplan.adapter.MealtimeListItem
import io.reactivex.disposables.CompositeDisposable
import java.util.Date
import timber.log.Timber

class WeekplanViewModel(
    private val mealtimeRepository: MealtimeRepository
) : ViewModel() {

    private val _mealtimesState = MutableLiveData<ViewState<List<MealtimeListItem>>>()
    val mealtimesState: LiveData<ViewState<List<MealtimeListItem>>>
        get() = _mealtimesState

    private val compositeDisposable = CompositeDisposable()

    fun loadMealtimesByDate(date: Date) {
        _mealtimesState.value = ViewState.Loading
        compositeDisposable.add(
            mealtimeRepository.getAllMealtimeByDate(date)
                .applySchedulers()
                .subscribe({ mealtimes ->
                    _mealtimesState.value = if (mealtimes.isNotEmpty()) {
                        ViewState.Success(groupMealsByCategory(mealtimes))
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _mealtimesState.value = ViewState.Error(error)
                })
        )
    }

    fun addCreatedMealtime(name: String, quantity: Int, gram: Int) {
        compositeDisposable.add(
            mealtimeRepository.insertMealtime(Mealtime(recipe = Recipe(name = name), quantity = quantity, gram = gram))
                .applySchedulers()
                .subscribe({}, { error ->
                    Timber.tag(WEEKPLAN_VIEW_MODEL).e(error)
                })
        )
    }

    private fun groupMealsByCategory(meals: List<Mealtime>): List<MealtimeListItem> {
        val categoryOrder = MealtimeType.entries.toTypedArray()

        val grouped = meals
            .groupBy { it.type }
            .toSortedMap(compareBy { categoryOrder.indexOf(it) })

        val result = mutableListOf<MealtimeListItem>()
        for ((category, mealsInCategory) in grouped) {
            result.add(MealtimeListItem.Header(category))
            result.addAll(mealsInCategory.map { MealtimeListItem.MealtimeItem(it) })
        }

        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val WEEKPLAN_VIEW_MODEL = "WeekplanViewModel"
    }
}
