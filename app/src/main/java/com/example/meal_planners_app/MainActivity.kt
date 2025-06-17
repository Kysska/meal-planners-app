package com.example.meal_planners_app

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.meal_planners_app.databinding.ActivityMainBinding
import com.example.meal_planners_app.di.DiProvider
import com.example.meal_planners_app.di.SubComponents
import com.example.mealtime.di.MealtimeRepositoryModule
import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeRepository
import com.example.product.domain.Product
import com.example.product.domain.ProductRepository
import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.utils.util.applySchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SubComponents {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var mealtimeRepository: MealtimeRepository

    @Inject
    lateinit var recipeRepository: RecipeRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiProvider.appComponent().inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
