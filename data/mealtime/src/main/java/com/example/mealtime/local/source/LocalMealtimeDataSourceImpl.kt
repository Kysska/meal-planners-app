package com.example.mealtime.local.source

import com.example.mealtime.domain.Mealtime
import com.example.mealtime.local.dao.MealtimeDao
import com.example.mealtime.local.mapper.MealtimeDatabaseMapper
import com.example.mealtime.repository.source.LocalMealtimeDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date
import timber.log.Timber

class LocalMealtimeDataSourceImpl(
    private val mealtimeDao: MealtimeDao,
    private val databaseMapper: MealtimeDatabaseMapper
) : LocalMealtimeDataSource {

    override fun getAllMealtimeByDate(date: Date): Observable<List<Mealtime>> {
        return mealtimeDao.getAllMealtimeByDate(date)
            .map { mealTimes ->
                mealTimes.map { databaseMapper.reverseMap(it.mealtime, it.recipe) }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun insertMealtime(mealtime: Mealtime): Completable {
        val mealtimeDb = databaseMapper.map(mealtime)
        return mealtimeDao.insertMealtime(mealtimeDb)
            .doOnComplete {
                Timber.tag(TAG).d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun updateMealtime(mealtime: Mealtime): Completable {
        val mealtimeDb = databaseMapper.map(mealtime)
        return mealtimeDao.updateMealtime(mealtimeDb)
            .doOnComplete {
                Timber.tag(TAG).d("Update completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun deleteMealtime(mealtime: Mealtime): Completable {
        val mealtimeDb = databaseMapper.map(mealtime)
        return mealtimeDao.deleteMealtime(mealtimeDb)
            .doOnComplete {
                Timber.tag(TAG).d("Delete completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getDailyKcalSummary(date: Date): Single<Int> {
        return mealtimeDao.getDailyKcalSummary(date)
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getDailyProteinSummary(date: Date): Single<Float> {
        return mealtimeDao.getDailyProteinSummary(date)
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getDailyFatsSummary(date: Date): Single<Float> {
        return mealtimeDao.getDailyFatsSummary(date)
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getDailyCarbsSummary(date: Date): Single<Float> {
        return mealtimeDao.getDailyCarbsSummary(date)
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    companion object {
        private const val TAG = "LocalMealtimes"
    }
}
