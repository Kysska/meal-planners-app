package com.example.weekplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ui.extensions.validateInputs
import com.example.weekplan.databinding.FragmentMealtimeAddBinding
import com.example.weekplan.di.WeekplanComponentProvider
import javax.inject.Inject

class MealtimeAddFragment : Fragment(R.layout.fragment_mealtime_add) {

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
            quantityTextInput.validateInputs()
            grammInputLayout.validateInputs()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }
}
