package com.example.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.product.domain.ProductInCart
import com.example.shoppinglist.ProductCreateFragment.Companion.KEY_DATE
import com.example.shoppinglist.databinding.FragmentProductListBinding
import com.example.shoppinglist.di.ShoppingListComponentProvider
import com.example.ui.adapter.ProductsCheckboxAdapter
import com.example.ui.extensions.format
import com.example.ui.view.ViewState
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date
import javax.inject.Inject
import timber.log.Timber

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Date = Date()
    private val productsCheckboxAdapter by lazy {
        ProductsCheckboxAdapter { product ->
            Timber.tag("ProductListFragment").d(product.toString())
            productViewModel.updateProductToCart(product.copy(selected = !product.selected, description = "dgjklgj"))
        }
    }

    @Inject
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent =
            (requireActivity() as ShoppingListComponentProvider).provideShoppingListComponent()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = productsCheckboxAdapter

        updateDateText()

        binding.dateTextView.setOnClickListener {
            showDatePicker()
        }

        binding.addMealtimeButton.setOnClickListener {
            opeProductAddFragment()
        }

        observeViewModel()
        productViewModel.loadProductsByDate(selectedDate)
    }

    private fun observeViewModel() {
        productViewModel.productsInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    if (state.data.isEmpty()) {
                        binding.empty.visibility = View.VISIBLE
                    } else {
                        Timber.tag("ProductListFragment").d(state.data.toString())
                        binding.empty.visibility = View.GONE
                        productsCheckboxAdapter.submitList(state.data)
                    }
                }
                is ViewState.Error -> {}
                else -> {}
            }
        }
    }

    private fun opeProductAddFragment() {
        val bundle = Bundle()
        bundle.putLong(KEY_DATE, selectedDate.time)
        findNavController().navigate(com.example.ui.R.id.productAddFragment, bundle)
    }

    private fun updateDateText() {
        binding.dateTextView.text = requireActivity().getString(com.example.ui.R.string.date_with_calendar, selectedDate.format())
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(com.example.ui.R.string.planner_fragment_picker))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
            .run {
                addOnPositiveButtonClickListener { dateInMillis ->
                    selectedDate = Date(dateInMillis)
                    updateDateText()

                    productsCheckboxAdapter.submitList(emptyList())
                    productViewModel.loadProductsByDate(selectedDate)
                }
                show(this@ProductListFragment.requireActivity().supportFragmentManager, DATE_PICKER_TAG)
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
