package com.example.weekplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.search.SearchFragment
import com.example.ui.extensions.loadImage
import com.example.ui.extensions.validateInputs
import com.example.ui.screens.RecipeDetailsFragment
import com.example.ui.utils.SearchMode
import com.example.ui.utils.SearchTypeData
import com.example.ui.vo.RecipeView
import com.example.weekplan.databinding.FragmentMealtimeAddBinding
import com.example.weekplan.di.WeekplanComponentProvider
import com.example.weekplan.utils.toMealtimeType
import com.google.android.material.chip.Chip
import java.util.Date
import javax.inject.Inject

class MealtimeCreateFragment : Fragment(R.layout.fragment_mealtime_add) {

    private var _binding: FragmentMealtimeAddBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var weekplanViewModel: WeekplanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent = (requireActivity() as WeekplanComponentProvider).providePlannerComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealtimeAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            includedRecipeItem.root.setOnClickListener {
                navigateToSearchFragment()
            }
            addRecipeButton.setOnClickListener {
                onSaveButtonClicked()
            }
        }

        parentFragmentManager.setFragmentResultListener(
            SearchFragment.KEY_RECIPE_SELECTION,
            viewLifecycleOwner
        ) { _, result ->
            val recipe = result.getParcelable<RecipeView>(SearchFragment.KEY_RECIPE_VIEW)
            recipe?.let { updateRecipeSelectionUI(recipe) }
        }
    }

    private fun getDate(): Date {
        val date = requireArguments().getLong(KEY_DATE)
        return Date(date)
    }

    private fun navigateToSearchFragment() {
        val bundle = Bundle()
        bundle.putString(SearchFragment.KEY_STRING, SearchTypeData.RECOMMENDATION.name)
        bundle.putString(SearchFragment.KEY_SEARCH_MODE, SearchMode.EDIT.name)
        findNavController().navigate(com.example.ui.R.id.searchFragment, args = bundle)
    }

    private fun navigateToRecipeDetails(id: Int) {
        val bundle = Bundle()
        bundle.putInt(RecipeDetailsFragment.KEY_ID, id)
        findNavController().navigate(com.example.ui.R.id.recipeDetailsFragment, args = bundle)
    }

    private fun updateRecipeSelectionUI(recipeView: RecipeView) {
        binding.includedRecipeItem.apply {
            tvTitle.text = recipeView.name
            imageFood.loadImage(recipeView.image)
            tvTime.text = recipeView.times

            tvRecipeDetail.setOnClickListener {
                navigateToRecipeDetails(recipeView.id)
            }
        }

        weekplanViewModel.setSelectedRecipe(recipeView)
    }

    private fun validateForm(): Boolean {
        var isValid = true

        binding.apply {
            if (!quantityTextInput.validateInputs()) {
                isValid = false
            }
            if (!grammInputLayout.validateInputs()) {
                isValid = false
            }
            if (chipGroupCategories.checkedChipId == View.NO_ID) {
                isValid = false
                Toast.makeText(requireContext(),
                    getString(R.string.choose_category), Toast.LENGTH_SHORT).show()
            }
            if (weekplanViewModel.selectedRecipe.value == null) {
                isValid = false
                Toast.makeText(requireContext(),
                    getString(R.string.choose_recipe), Toast.LENGTH_SHORT).show()
            }
        }

        return isValid
    }

    private fun onSaveButtonClicked() {
        if (validateForm()) {
            binding.apply {
                val selectedChip = chipGroupCategories.findViewById<Chip>(chipGroupCategories.checkedChipId)
                val mealtimeType = selectedChip.text.toString().toMealtimeType()

                weekplanViewModel.selectedRecipe.value?.let {
                    weekplanViewModel.addCreatedMealtime(
                        quantity = quantityEditText.text.toString().toInt(),
                        gram = gramEditText.text.toString().toInt(),
                        recipeId = it.id,
                        type = mealtimeType,
                        date = getDate()
                    )
                }
            }
            weekplanViewModel.removeSelectedRecipe()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_DATE = "date"
    }
}
