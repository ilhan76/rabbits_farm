package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.CageSimpleDto
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.DeathRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import com.kudashov.rabbits_farm.repository.TaskRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class TaskRepositoryHeroku : TaskRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getTasks(token: String, isDone: Boolean): Observable<TaskResponse> {
        val response: PublishSubject<TaskResponse> = PublishSubject.create()

        return response
    }

    override fun putDeath(
        token: String,
        farmNumber: Int?,
        cageNumber: Int?,
        letter: String?,
        deathCause: String?
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .death(
                token,
                DeathRequest(
                    CageSimpleDto(farmNumber, cageNumber, letter),
                    deathCause
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "putDeath: Success")
                response.onNext(it)
            }, {
                Log.d(TAG, "putDeath: Error ${it.localizedMessage}")
                response.onError(it)
            })

        return response
    }
}