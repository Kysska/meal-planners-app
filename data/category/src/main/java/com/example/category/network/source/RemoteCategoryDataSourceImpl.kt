package com.example.category.network.source

import com.example.category.domain.Category
import com.example.category.network.CategoryApiInterface
import com.example.category.network.mapper.CategoryNetworkMapper
import com.example.category.repository.source.RemoteCategoryDataSource
import io.reactivex.Single
import timber.log.Timber

internal class RemoteCategoryDataSourceImpl(
    private val apiInterface: CategoryApiInterface,
    private val networkMapper: CategoryNetworkMapper
) : RemoteCategoryDataSource {
    override fun getCategories(): Single<List<Category>> {
        return apiInterface.getCategories()
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }
}
