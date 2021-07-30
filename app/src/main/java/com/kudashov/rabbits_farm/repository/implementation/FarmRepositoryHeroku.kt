package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.WeightRequest
import com.kudashov.rabbits_farm.net.response.farm.*
import com.kudashov.rabbits_farm.repository.FarmRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class FarmRepositoryHeroku : FarmRepository {
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
            .subscribe(object : Observer<RabbitResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(
                        RabbitResponse(
                            e?.localizedMessage,
                            0,
                            null
                        )
                    )
                }

                override fun onNext(t: RabbitResponse?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
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
            .subscribe(object : Observer<CageResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(
                        CageResponse(
                            e?.localizedMessage,
                            null
                        )
                    )
                }

                override fun onNext(t: CageResponse?) {
                    Log.d(TAG, "onNext: count of cage: ${t?.results?.size}")
                    //Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
            })

        return resp
    }

    override fun getRabbitMoreInf(token: String, id: Int): Observable<RabbitMoreInfResponse> {
        val response: PublishSubject<RabbitMoreInfResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RabbitMoreInfDto> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: RabbitMoreInfDto?) {
                    Log.d(TAG, "onNext: $t")
                    response.onNext(
                        RabbitMoreInfResponse(
                            t,
                            ""
                        )
                    )
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(
                        RabbitMoreInfResponse(
                            null,
                            e?.localizedMessage
                        )
                    )
                }

            })

        return response
    }

    override fun getOperations(token: String, id: Int): Observable<OperationsResponse> {
        val response: PublishSubject<OperationsResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<OperationsResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: OperationsResponse?) {
                    Log.d(TAG, "onNext: $t")
                    response.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(
                        OperationsResponse(
                            null,
                            e?.localizedMessage
                        )
                    )
                }
            })

        return response
    }

    override fun postWeight(token: String, pathType: String, id: Int, weight: Double) : Observable<PostWeightResponse> {
        val response: PublishSubject<PostWeightResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .postWeight(token, pathType, id, WeightRequest(weight = weight))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PostWeightResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: PostWeightResponse?) {
                    Log.d(TAG, "onNext: The data was sent successfully")
                    response.onNext(PostWeightResponse(null))
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(PostWeightResponse(e?.localizedMessage))
                }
            })
        return response
    }
}