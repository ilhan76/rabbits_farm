package com.kudashov.rabbits_farm.repository.implementation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.*
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.USER_TOKEN
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
    ): Observable<RabbitResponse> {
        val resp: PublishSubject<RabbitResponse> = PublishSubject.create()

        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

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
                    resp.onNext(RabbitResponse(e?.localizedMessage, 0, null))
                }

                override fun onNext(t: RabbitResponse?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
            })

        return resp
    }

    override fun getCages(): Observable<CageResponse> {
        val resp: PublishSubject<CageResponse> = PublishSubject.create()

        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        ApiClient.client.create(ApiInterface::class.java)
            .getCages(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CageResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    resp.onNext(CageResponse(e?.localizedMessage, null))
                }

                override fun onNext(t: CageResponse?) {
                    Log.d(TAG, "onNext: $t")
                    resp.onNext(t)
                }
            })

        return resp
    }

    override fun getTasks(isDone: Boolean): Observable<TaskResponse> {
        TODO("Not yet implemented")
    }

    override fun getBirth(isConfirmed: Boolean): Observable<BirthResponse> {
        TODO("Not yet implemented")
    }

    override fun getRabbitMoreInf(id: Int): Observable<RabbitMoreInfResponse> {
        val response: PublishSubject<RabbitMoreInfResponse> = PublishSubject.create()

        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        ApiClient.client.create(ApiInterface::class.java)
            .getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RabbitMoreInfDto> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: RabbitMoreInfDto?) {
                    Log.d(TAG, "onNext: $t")
                    response.onNext(RabbitMoreInfResponse(t, ""))
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(RabbitMoreInfResponse(null, e?.localizedMessage))
                }

            })

        return response
    }

    override fun getOperations(id: Int): Observable<OperationsResponse> {
        val response: PublishSubject<OperationsResponse> = PublishSubject.create()

        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

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
                    response.onNext(OperationsResponse(null, e?.localizedMessage))
                }
            })

        return response
    }

    override fun postWeight(token: String, type: String, id: Int, weight: Double) {
        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        ApiClient.client.create(ApiInterface::class.java)
            .postWeight(token, type, id, weight)
    }
}