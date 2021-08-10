package com.kudashov.rabbits_farm.data.source.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.source.FarmProvider
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.farm.WeightRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.farm.CageResponse
import com.kudashov.rabbits_farm.net.response.farm.OperationsResponse
import com.kudashov.rabbits_farm.net.response.farm.RabbitMoreInfResponse
import com.kudashov.rabbits_farm.net.response.farm.RabbitResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class FarmProviderImpl : FarmProvider {
    private val TAG: String = this::class.java.simpleName

    override fun getRabbits(
        token: String,
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        type: List<String>?,
        breed: Int?,
        status: String?,
        ageFrom: Int?,
        ageTo: Int?,
        cageNumberFrom: Int?,
        cageNumberTo: Int?,
        isMale: Int?,
        orderBy: String?
    ): Observable<RabbitResponse> {
        val resp: PublishSubject<RabbitResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbits(
                token,
                page,
                pageSize,
                farmNumber,
                type,
                breed,
                status,
                ageFrom,
                ageTo,
                cageNumberFrom,
                cageNumberTo,
                isMale,
                orderBy
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $it")
                resp.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                resp.onNext(
                    RabbitResponse(
                        it?.localizedMessage,
                        0,
                        null
                    )
                )
            })

        return resp
    }

    override fun getCages(
        token: String,
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        status: List<String>?,
        type: String?,
        isParallel: Int?,
        numberRabbitsFrom: Int?,
        numberRabbitsTo: Int?,
        orderBy: String?
    ): Observable<CageResponse> {
        val resp: PublishSubject<CageResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getCages(
                token,
                page,
                pageSize,
                farmNumber,
                status,
                type,
                isParallel,
                numberRabbitsFrom,
                numberRabbitsTo,
                orderBy
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: count of cage: ${it?.results?.size}")
                resp.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                resp.onNext(
                    CageResponse(
                        it?.localizedMessage,
                        null
                    )
                )
            })

        return resp
    }

    override fun getRabbitMoreInf(token: String, id: Int): Observable<RabbitMoreInfResponse> {
        val response: PublishSubject<RabbitMoreInfResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $it")
                response.onNext(
                    RabbitMoreInfResponse(
                        it,
                        ""
                    )
                )
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                response.onNext(
                    RabbitMoreInfResponse(
                        null,
                        it?.localizedMessage
                    )
                )
            })

        return response
    }

    override fun getOperations(token: String, id: Int): Observable<OperationsResponse> {
        val response: PublishSubject<OperationsResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $it")
                response.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                response.onNext(
                    OperationsResponse(
                        null,
                        it?.localizedMessage
                    )
                )
            })

        return response
    }

    override fun postWeight(
        token: String,
        pathType: String,
        id: Int,
        weight: Double
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .postWeight(token, pathType, id, WeightRequest(weight = weight))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: The data was sent successfully")
                response.onNext(
                    BaseResponse(
                        null
                    )
                )
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                response.onNext(
                    BaseResponse(
                        it?.localizedMessage
                    )
                )
            })
        return response
    }
}