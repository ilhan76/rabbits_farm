package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.converters.BirthConverter
import com.kudashov.rabbits_farm.data.domain.BirthDomain
import com.kudashov.rabbits_farm.data.source.BirthProvider
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.birth.ConfirmPregnancyRequest
import com.kudashov.rabbits_farm.net.request.birth.TakeBirthRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import com.kudashov.rabbits_farm.repository.BirthRepository
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class BirthRepositoryImpl(
    val converter: BirthConverter,
    val provider: BirthProvider
) : BirthRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getBirth(
        token: String,
        page: Int,
        pageSize: Int,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<BirthDomain>>>> {
        val response: PublishSubject<Pair<Int, RepoResponse<List<BirthDomain>>>> =
            PublishSubject.create()

        provider.getBirth(token, page, pageSize, isConfirmed, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                if (resp.results == null || resp.results.isEmpty()) {
                    Log.d(TAG, "getBirth: No Item")
                    response.onNext(
                        Pair(
                            0,
                            RepoResponse<List<BirthDomain>>(null, ERROR_NO_ITEM)
                        )
                    )
                } else {
                    Log.d(TAG, "getBirth: Success")
                    response.onNext(
                        Pair(
                            resp.count,
                            RepoResponse(
                                resp.results.map { BirthDto ->
                                    converter.convertBirthFromApiToDomain(BirthDto)
                                }, resp.detail
                            )
                        )
                    )
                }
            }, {
                Log.d(TAG, "getBirth: Error ${it.localizedMessage}")
                response.onError(it)
            })

        return response
    }

    override fun confirmPregnancy(
        token: String,
        id: Int,
        confirm: ConfirmPregnancyRequest
    ): Observable<BaseResponse> {
        val response: PublishSubject<BaseResponse> = PublishSubject.create()

        provider.confirmPregnancy(token, id, confirm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "confirmPregnancy: put success")
                response.onNext(it)
            }, {
                Log.d(TAG, "confirmPregnancy: ${it?.localizedMessage}")
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

        provider.takeBirth(token, id, count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "takeBirth: Put Success")
                response.onNext(it)
            }, {
                Log.d(TAG, "takeBirth: ${it?.localizedMessage}")
                response.onNext(BaseResponse(it?.localizedMessage))
            })

        return response
    }
}