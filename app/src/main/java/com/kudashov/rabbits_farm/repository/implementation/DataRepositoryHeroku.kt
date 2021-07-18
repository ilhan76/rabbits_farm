package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.RabbitDto
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

class DataRepositoryHeroku: DataRepository {

    private val TAG: String? = DataRepositoryHeroku::class.java.simpleName

    override fun getRabbits(): Observable<RabbitServerResponse> {
        val resp: PublishSubject<RabbitServerResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbits()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RabbitDto>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: List<RabbitDto>?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(RabbitServerResponse("", t))
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(RabbitServerResponse(e?.localizedMessage, null))
                }
            })

        return resp
    }

    override fun getCages(): Observable<CageServerResponse> {
        TODO("Not yet implemented")
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