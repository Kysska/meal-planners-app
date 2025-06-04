package com.example.category.network

import com.example.category.repository.source.RemoteCategoryDataSource
import com.example.core.mapper.CategoryNetworkMapper
import com.example.core.model.Category
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
