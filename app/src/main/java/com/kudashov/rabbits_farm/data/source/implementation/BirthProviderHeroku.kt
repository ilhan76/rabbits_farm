package com.kudashov.rabbits_farm.data.source.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.source.BirthProvider
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.birth.ConfirmPregnancyRequest
import com.kudashov.rabbits_farm.net.request.birth.TakeBirthRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class BirthProviderHeroku: BirthProvider {
    private val TAG: String = this::class.java.simpleName

    override fun getBirth(
        token: String,
        page: Int,
        pageSize: Int,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<BirthResponse> {
        val response: PublishSubject<BirthResponse> = PublishSubject.create()

        if (isConfirmed) {
            ApiClient.client.create(ApiInterface::class.java)
                .getBirthConfirmed(
                    token,
                    page,
                    pageSize,
                    orderBy
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "onNext: $it")
                    response.onNext(it)
                }, {
                    Log.d(TAG, "onError: ${it?.localizedMessage}")
                    response.onNext(BirthResponse(it?.localizedMessage, null))
                })
        } else {
            ApiClient.client.create(ApiInterface::class.java)
                .getBirthUnconfirmed(
                    token,
                    page,
                    pageSize,
                    orderBy
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "onNext: $it")
                    response.onNext(it)
                }, {
                    Log.d(TAG, "onError: ${it?.localizedMessage}")
                    response.onNext(BirthResponse(it?.localizedMessage, null))
                })
        }
        return response
    }

    override fun confirmPregnancy(
        token: String,
        id: Int,
        confirm: ConfirmPregnancyRequest
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .confirmPregnancy(token, id, confirm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: put success")
                response.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                response.onNext(BaseResponse(it?.localizedMessage))
            })

        return response
    }

    override fun takeBirth(
        token: String,
        id: Int,
        count: TakeBirthRequest
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .takeBirth(token, id, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: put success")
                response.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                response.onNext(BaseResponse(it?.localizedMessage))
            })

        return response
    }
}