package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.ConfirmRequest
import com.kudashov.rabbits_farm.net.request.TakeBirthRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import com.kudashov.rabbits_farm.repository.BirthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class BirthRepositoryHeroku : BirthRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getBirth(
        token: String,
        page: Int,
        pageSize: Int,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<BirthResponse> {
        val response: PublishSubject<BirthResponse> = PublishSubject.create()

        if (isConfirmed){
            ApiClient.client.create(ApiInterface::class.java)
                .getBirthConfirmed(
                    token,
                    page,
                    pageSize,
                    orderBy
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BirthResponse> {
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
                .subscribe(object : Observer<BirthResponse> {
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
        }
        return response
    }

    override fun confirmPregnancy(
        token: String,
        id: Int,
        confirm: ConfirmRequest
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        ApiClient.client.create(ApiInterface::class.java)
            .confirmPregnancy(token, id, confirm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse>{
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: BaseResponse?) {
                    Log.d(TAG, "onNext: put success")
                    response.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(BaseResponse(e?.localizedMessage))
                }
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
            .subscribe(object : Observer<BaseResponse>{
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: BaseResponse?) {
                    Log.d(TAG, "onNext: put success")
                    response.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(BaseResponse(e?.localizedMessage))
                }
            })

        return response
    }
}