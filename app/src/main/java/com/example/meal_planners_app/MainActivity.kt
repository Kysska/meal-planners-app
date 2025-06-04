package com.example.meal_planners_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.meal_planners_app.databinding.ActivityMainBinding
import com.example.meal_planners_app.di.DiProvider
import com.example.meal_planners_app.di.SubComponents
import com.example.recipe.domain.RecipeRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SubComponents {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var recipeRepository: RecipeRepository

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
