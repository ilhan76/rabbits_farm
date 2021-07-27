package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.*
import com.kudashov.rabbits_farm.repository.DataRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class DataRepositoryHeroku : DataRepository {

    private val TAG: String? = DataRepositoryHeroku::class.java.simpleName

    override fun getRabbits(
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
    ): Observable<RabbitServerResponse> {
        val resp: PublishSubject<RabbitServerResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbits(
                "Token 1324123412342134123",
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
            .subscribe(object : Observer<RabbitServerResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(RabbitServerResponse(e?.localizedMessage, 0, null))
                }

                override fun onNext(t: RabbitServerResponse?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
            })

        return resp
    }

    override fun getCages(): Observable<CageServerResponse> {
        val resp: PublishSubject<CageServerResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getCages("Token 1324123412342134123")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CageServerResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(CageServerResponse(e?.localizedMessage, null))
                }

                override fun onNext(t: CageServerResponse?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
            })

        return resp
    }

    override fun getTasks(isDone: Boolean): Observable<TaskServerResponse> {
        TODO("Not yet implemented")
    }

    override fun getBirth(isConfirmed: Boolean): Observable<BirthServerResponse> {
        TODO("Not yet implemented")
    }

    override fun getOperations(): Observable<OperationsResponse> {
        TODO("Not yet implemented")
    }
}