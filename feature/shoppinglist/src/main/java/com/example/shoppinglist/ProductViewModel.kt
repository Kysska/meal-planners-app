package com.example.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.product.domain.Product
import com.example.product.domain.ProductRepository
import com.example.ui.view.ViewState
import com.example.utils.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.Date
import timber.log.Timber

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productsState = MutableLiveData<ViewState<List<Product>>>()
    val productsState: LiveData<ViewState<List<Product>>>
        get() = _productsState

    private val compositeDisposable = CompositeDisposable()

    fun loadProductsByDate(date: Date) {
        _productsState.value = ViewState.Loading
        compositeDisposable.add(
            productRepository.getProductsInShopCart(date)
                .applySchedulers()
                .subscribe({ products ->
                    _productsState.value = if (products.isNotEmpty()) {
                        ViewState.Success(products)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _productsState.value = ViewState.Error(error)
                })
        )
    }

    fun loadProductsByQuery(query: String) {
        _productsState.value = ViewState.Loading
        compositeDisposable.add(
            productRepository.getProductsByQuery(query)
                .applySchedulers()
                .subscribe({ products ->
                    _productsState.value = if (products.isNotEmpty()) {
                        ViewState.Success(products)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _productsState.value = ViewState.Error(error)
                })
        )
    }

    fun loadAllProducts() {
        _productsState.value = ViewState.Loading
        compositeDisposable.add(
            productRepository.getProducts()
                .applySchedulers()
                .subscribe({ products ->
                    _productsState.value = if (products.isNotEmpty()) {
                        ViewState.Success(products)
                    } else {
                        ViewState.Success(emptyList())
                    }
                }, { error ->
                    _productsState.value = ViewState.Error(error)
                })
        )
    }

    fun addProductToCart(product: Product) {
        compositeDisposable.add(
            productRepository.addProductInShopCart(product)
                .applySchedulers()
                .subscribe({}, { error ->
                    Timber.tag(PRODUCT_VIEW_MODEL).e(error)
                })
        )
    }

    fun updateProductToCart(product: Product) {
        compositeDisposable.add(
            productRepository.selectedProductInShopCart(product)
                .applySchedulers()
                .subscribe({}, { error ->
                    Timber.tag(PRODUCT_VIEW_MODEL).e(error)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val PRODUCT_VIEW_MODEL = "ProductViewModel"
    }
}
