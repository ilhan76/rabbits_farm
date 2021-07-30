package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.auth.AuthResponse
import com.kudashov.rabbits_farm.repository.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class AuthRepositoryHeroku: AuthRepository {
    private val TAG: String = this::class.java.simpleName

    override fun auth(username: String, pass: String): Observable<AuthResponse> {
        val response: PublishSubject<AuthResponse> = PublishSubject.create()
        Log.d(TAG, "auth: $username")
        Log.d(TAG, "auth: $pass")

        ApiClient.client.create(ApiInterface::class.java)
            .getToken(UserDto(username, pass))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AuthResponse>{
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(t: AuthResponse?) {
                    Log.d(TAG, "onNext: $t")
                    response.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: ${e?.localizedMessage}")
                    response.onNext(
                        AuthResponse(
                            null,
                            null,
                            e?.localizedMessage
                        )
                    )
                }
            })

        return response
    }

}