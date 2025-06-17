package com.example.category.local.source

import com.example.category.domain.Category
import com.example.category.local.dao.CategoryDao
import com.example.category.local.mapper.CategoryDatabaseMapper
import com.example.category.repository.source.LocalCategoryDataSource
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

class LocalCategoryDataSourceImpl(
    private val categoryDao: CategoryDao,
    private val databaseMapper: CategoryDatabaseMapper
) : LocalCategoryDataSource {
    override fun addCategory(category: Category): Completable {
        val categoryDb = databaseMapper.map(category)
        return categoryDao.insertCategory(categoryDb)
            .doOnComplete {
                Timber.tag(TAG).d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getAllCategories(): Single<List<Category>> {
        return categoryDao.getAllCategories()
            .map { response -> databaseMapper.reverseMap(response) }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    companion object {
        private const val TAG = "LocalCategories"
    }
}
