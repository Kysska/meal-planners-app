package com.example.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.product.domain.Product
import com.example.shoppinglist.databinding.FragmentProductCreateBinding
import com.example.shoppinglist.di.ShoppingListComponentProvider
import com.example.ui.adapter.ProductsAdapter
import com.example.ui.view.SearchBar
import com.example.ui.view.ViewState
import javax.inject.Inject

class ProductCreateFragment : Fragment(R.layout.fragment_product_create), SearchBar.OnSearchActionListener, SearchBar.OnClearSearchClickListener {

    private var _binding: FragmentProductCreateBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var productViewModel: ProductViewModel

    private val productsAdapter by lazy {
        ProductsAdapter { product: Product ->
            productViewModel.addProductToCart(product.copy(date = getDate()))
            findNavController().navigateUp()
        }
    }

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
        _binding = FragmentProductCreateBinding.inflate(inflater, container, false)
        binding.searchBar.setOnClearButtonClickListener(this)
        binding.searchBar.setOnSearchActionListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            rvRecommendations.adapter = productsAdapter
        }

        observeViewModel()
        productViewModel.loadAllProducts()
    }

    private fun observeViewModel() {
        productViewModel.productsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    if (state.data.isNotEmpty()) {
                        productsAdapter.submitList(state.data)
                    } else if (binding.searchBar.getText().isNotEmpty()) {
                        productsAdapter.submitList(listOf(Product(name = binding.searchBar.getText())))
                    }
                }
                is ViewState.Error -> {}
                else -> {}
            }
        }
    }

    private fun getDate(): java.util.Date {
        val date = requireArguments().getLong(KEY_DATE)
        return java.util.Date(date)
    }

    override fun onSearchSubmitted(query: String) {
        productViewModel.loadProductsByQuery(query)
    }

    override fun onClearButtonClicked() {
        binding.searchBar.clear()
        productsAdapter.submitList(listOf())
        productViewModel.loadAllProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_DATE = "selectedDate"
    }
}
