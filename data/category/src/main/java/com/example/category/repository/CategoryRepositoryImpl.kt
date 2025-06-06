package com.example.category.repository

import com.example.category.domain.Category
import com.example.category.domain.CategoryRepository
import com.example.category.repository.source.LocalCategoryDataSource
import com.example.category.repository.source.RemoteCategoryDataSource
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

internal class CategoryRepositoryImpl(
    private val remoteCategoryDataSource: RemoteCategoryDataSource,
    private val localCategoryDataSource: LocalCategoryDataSource
) : CategoryRepository {
    override fun getCategories(): Single<List<Category>> {
        return remoteCategoryDataSource.getCategories()
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { category ->
                        addCategory(category)
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(CATEGORY_REPOSITORY).e(throwable, "Error fetching categories from network, loading from local")
                localCategoryDataSource.getAllCategories()
            }
    }

    private fun addCategory(category: Category): Completable {
        return localCategoryDataSource.addCategory(category)
    }

    companion object {
        private const val CATEGORY_REPOSITORY = "CategoryRepository"
    }
}
