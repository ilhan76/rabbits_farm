package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.converters.TaskConverter
import com.kudashov.rabbits_farm.data.domain.TaskListItemType
import com.kudashov.rabbits_farm.data.source.TaskProvider
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.net.response.task.DeathResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import com.kudashov.rabbits_farm.repository.TaskRepository
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class TaskRepositoryImpl(
    private val converter: TaskConverter,
    private val provider: TaskProvider
) : TaskRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getTasks(
        token: String,
        isDone: Boolean,
        page: Int,
        pageSize: Int,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<TaskListItemType>>>> {
        val response: PublishSubject<Pair<Int, RepoResponse<List<TaskListItemType>>>> =
            PublishSubject.create()
        provider.getTasks(token, isDone, page, pageSize, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                if (resp.tasks == null || resp.tasks.isEmpty()) {
                    Log.d(TAG, "getTasks: No Item")
                    response.onNext(
                        Pair(
                            resp.count,
                            RepoResponse<List<TaskListItemType>>(null, ERROR_NO_ITEM)
                        )
                    )
                } else {
                    Log.d(TAG, "getTasks: Success")
                    response.onNext(
                        Pair(
                            resp.count,
                            RepoResponse(
                                resp.tasks.map { taskDto ->
                                    converter.convertTaskItemFromApiToDomain(task = taskDto, isComplete = isDone)
                                }, resp.detail
                            )
                        )
                    )
                }
            }, {
                Log.d(TAG, "getTasks: Error ${it.localizedMessage}")
                response.onError(it)
            })
        return response
    }

    override fun confirmSimpleTask(token: String, id: Int): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()
        provider.confirmSimpleTask(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmSimpleTask: Success")
                response.onNext(BaseResponse(null))
            }, {
                Log.d(TAG, "confirmSimpleTask: Error ${it.localizedMessage}")
                response.onError(it)
            })
        return response
    }

    override fun confirmSlaughterInspectionTask(
        token: String,
        id: Int,
        weights: List<Double>
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()
        provider.confirmSlaughterInspectionTask(token, id, weights)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmSlaughterInspectionTask: Success")
                response.onNext(BaseResponse(null))
            }, {
                Log.d(TAG, "confirmSlaughterInspectionTask: Error ${it.localizedMessage}")
                response.onError(it)
            })
        return response
    }

    override fun confirmDepositionFromMotherTask(
        token: String,
        id: Int,
        countMales: Int
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()
        provider.confirmDepositionFromMotherTask(token, id, countMales)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmDepositionFromMotherTask: Success")
                response.onNext(BaseResponse(null))
            }, {
                Log.d(TAG, "confirmDepositionFromMotherTask: Error ${it.localizedMessage}")
                response.onError(it)
            })
        return response
    }

    override fun putDeath(
        token: String,
        farmNumber: Int?,
        cageNumber: Int?,
        letter: String?,
        deathCause: String?
    ): Observable<DeathResponse> {
        return provider.putDeath(token, farmNumber, cageNumber, letter, deathCause)
    }
}