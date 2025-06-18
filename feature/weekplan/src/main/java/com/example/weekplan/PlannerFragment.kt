package com.example.weekplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ui.extensions.format
import com.example.ui.view.ViewState
import com.example.weekplan.adapter.MealtimesAdapter
import com.example.weekplan.databinding.FragmentPlannerBinding
import com.example.weekplan.di.WeekplanComponentProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date
import javax.inject.Inject

class PlannerFragment : Fragment(R.layout.fragment_planner) {

    private var _binding: FragmentPlannerBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Date = Date()
    private val mealtimesAdapter by lazy {
        return@lazy MealtimesAdapter()
    }

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
        _binding = FragmentPlannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDateText()

        binding.dateTextView.setOnClickListener {
            showDatePicker()
        }
        binding.addMealtimeButton.setOnClickListener {
            openMealtimeAddFragment()
        }

        observeViewModel()
    }

    private fun openMealtimeAddFragment() {
        findNavController().navigate(com.example.ui.R.id.mealtimeAddFragment)
    }

    private fun observeViewModel() {
        weekplanViewModel.mealtimesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    if (state.data.isEmpty()) {
                        binding.empty.visibility = View.VISIBLE
                    } else {
                        binding.empty.visibility = View.GONE
                        mealtimesAdapter.submitList(state.data)
                    }
                }
                is ViewState.Error -> {}
            }
        }

        weekplanViewModel.loadMealtimesByDate(selectedDate)
    }

    private fun updateDateText() {
        binding.dateTextView.text = requireActivity().getString(com.example.ui.R.string.date_with_calendar, selectedDate.format())
    }

    private fun showDatePicker() {
        val constraintsBuilder = CalendarConstraints.Builder().setValidator(
            DateValidatorPointForward.now())

        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.planner_fragment_picker))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
            .run {
                addOnPositiveButtonClickListener { dateInMillis ->
                    selectedDate = Date(dateInMillis)
                    updateDateText()

                    weekplanViewModel.loadMealtimesByDate(selectedDate)
                }
                show(this@PlannerFragment.requireActivity().supportFragmentManager, DATE_PICKER_TAG)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DATE_PICKER_TAG = "DATE_PICKER"
    }
}
