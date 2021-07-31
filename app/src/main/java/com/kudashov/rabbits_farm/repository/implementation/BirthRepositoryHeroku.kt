package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import com.kudashov.rabbits_farm.repository.BirthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class BirthRepositoryHeroku: BirthRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getBirth(
        token: String,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<BirthResponse> {
        val response: PublishSubject<BirthResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .getBirth(token, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BirthResponse>{
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: BirthResponse?) {
                    Log.d(TAG, "onNext: $t")
                    response.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(BirthResponse(e?.localizedMessage, null))
                }

            })
        return response
    }
}