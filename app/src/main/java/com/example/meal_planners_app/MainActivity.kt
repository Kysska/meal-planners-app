package com.example.meal_planners_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.util.applySchedulers
import com.example.meal_planners_app.di.DiProvider
import com.example.recipe.domain.RecipeRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var recipeRepository: RecipeRepository

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiProvider.appComponent().inject(this)
        compositeDisposable.add(
            recipeRepository.getRecipes()
                .applySchedulers()
                .subscribe({ recipes ->
                    Timber.tag("success").d(recipes.toString())
                }, { error ->
                    Timber.tag("error").e(error.toString())
                })
        )
    }
}