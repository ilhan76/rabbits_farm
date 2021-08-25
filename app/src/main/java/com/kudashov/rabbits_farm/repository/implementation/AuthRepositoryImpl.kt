package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.ApiWarning
import com.kudashov.rabbits_farm.net.response.auth.AuthResponse
import com.kudashov.rabbits_farm.repository.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class AuthRepositoryImpl: AuthRepository {
    private val TAG: String = this::class.java.simpleName

    override fun auth(username: String, pass: String): Observable<AuthResponse> {
        val response: PublishSubject<AuthResponse> = PublishSubject.create()
        Log.d(TAG, "auth: $username")
        Log.d(TAG, "auth: $pass")

        ApiClient.client.create(ApiInterface::class.java)
            .getToken(UserDto(username, pass))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $it")
                response.onNext(it)
            }, {
                Log.d(TAG, "onError: ${it.localizedMessage}")
                response.onNext(
                    AuthResponse(
                        null,
                        null,
                        ApiWarning(listOf(it.localizedMessage))
                    )
                )
            })

        return response
    }

}