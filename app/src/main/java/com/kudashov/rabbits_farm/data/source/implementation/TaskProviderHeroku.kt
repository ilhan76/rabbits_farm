package com.kudashov.rabbits_farm.data.source.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.CageSimpleDto
import com.kudashov.rabbits_farm.data.dto.TaskDto
import com.kudashov.rabbits_farm.data.source.TaskProvider
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.task.BunnyJiggingTaskRequest
import com.kudashov.rabbits_farm.net.request.task.DeathRequest
import com.kudashov.rabbits_farm.net.request.task.SlaughterInspectionTaskRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskProviderHeroku : TaskProvider {
    private val TAG: String = this::class.java.simpleName

    override fun getTasks(
        token: String,
        isDone: Boolean,
        page: Int,
        pageSize: Int,
        orderBy: String?
    ): Observable<TaskResponse> {
        val response: PublishSubject<TaskResponse> = PublishSubject.create()
/*        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(2000)
            response.onNext(
                TaskResponse(
                    "", listOf(
                        TaskDto(
                            id = 1,
                            type = "I",
                            date = "11.11.2021:sddvsd",
                            userId = 15,
                            cage = CageSimpleDto(1, 12, "a"),
                            maleCageTo = CageSimpleDto(1, 14, "a"),
                            countRabbit = 3
                        )
                    )
                )
            )
        }*/
        ApiClient.client.create(ApiInterface::class.java)
            .getTasks(token, if (isDone) 1 else 0, page, pageSize, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getTasks: Success")
                Log.d(TAG, "getTasks: ${it.tasks} - ${it.detail}")
                response.onNext(it)
            }, {
                Log.d(TAG, "getTasks: Error")
                response.onError(it)
            })
        return response
    }

    override fun confirmSimpleTask(token: String, id: Int): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .confirmSimpleTask(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmSimpleTask: Success")
                response.onNext(it)
            }, {
                Log.d(TAG, "confirmSimpleTask: Error")
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

        ApiClient.client.create(ApiInterface::class.java)
            .confirmSlaughterInspection(token, id, SlaughterInspectionTaskRequest(null, weights))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmSlaughterInspectionTask: Success")
                response.onNext(it)
            }, {
                Log.d(TAG, "confirmSlaughterInspectionTask: Error")
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

        ApiClient.client.create(ApiInterface::class.java)
            .confirmBunnyJigging(token, id, BunnyJiggingTaskRequest(null, countMales))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmDepositionFromMotherTask: Success")
                response.onNext(it)
            }, {
                Log.d(TAG, "confirmDepositionFromMotherTask: Error")
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