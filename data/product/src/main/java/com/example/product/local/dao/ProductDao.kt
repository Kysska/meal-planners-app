package com.example.product.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.product.local.dto.ProductDbEntity
import com.example.product.local.dto.ProductInCartDbEntity
import com.example.product.local.dto.ProductWithDate
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProducts(): Single<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Int): Single<ProductDbEntity>

    @Query("SELECT * FROM products WHERE category_id = :id")
    fun getProductByCategory(id: Int): Single<List<ProductDbEntity>>

    @Query("SELECT * FROM products WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' ")
    fun getProductByQuery(query: String): Single<List<ProductDbEntity>>

    @Query("SELECT * FROM products_in_cart ORDER BY date DESC")
    @Transaction
    fun getProductsInShopList(): Observable<List<ProductWithDate>>

    @Query("SELECT * FROM products_in_cart WHERE date IS NULL")
    @Transaction
    fun getProductsInShopCart(): Observable<List<ProductWithDate>>

    @Update
    @Transaction
    fun moveProductInShopList(product: ProductInCartDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun addProductInShopCart(product: ProductInCartDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: ProductDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(vararg product: ProductDbEntity): Completable
}