package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.converters.TaskConverter
import com.kudashov.rabbits_farm.data.domain.TaskListItemType
import com.kudashov.rabbits_farm.data.source.TaskProvider
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
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
    ): Observable<RepoResponse<List<TaskListItemType>>> {
        val response: PublishSubject<RepoResponse<List<TaskListItemType>>> = PublishSubject.create()
        provider.getTasks(token, isDone, page, pageSize, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                if (resp.tasks == null || resp.tasks.isEmpty()) {
                    Log.d(TAG, "getTasks: No Item")
                    response.onNext(
                        RepoResponse(null, ERROR_NO_ITEM)
                    )
                } else {
                    Log.d(TAG, "getTasks: Success")
                    response.onNext(
                        RepoResponse(
                            resp.tasks.map { taskDto ->
                                converter.convertTaskItemFromApiToDomain(task = taskDto)
                            }, resp.detail
                        )
                    )
                }
            }, {
                Log.d(TAG, "getTasks: Error ${it.localizedMessage}")
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
    ): Observable<BaseResponse> {
        return provider.putDeath(token, farmNumber, cageNumber, letter, deathCause)
    }
}