package com.kudashov.rabbits_farm.screens.auth

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.repository.AuthRepository
import com.kudashov.rabbits_farm.repository.implementation.AuthRepositoryImpl
import com.kudashov.rabbits_farm.utilits.*
import com.kudashov.rabbits_farm.utilits.const.*
import com.kudashov.rabbits_farm.utilits.const.messages.WARNING_AUTH
import com.kudashov.rabbits_farm.utilits.const.messages.WARNING_AUTH_PASSWORD
import com.kudashov.rabbits_farm.utilits.const.messages.WARNING_AUTH_USERNAME
import com.kudashov.rabbits_farm.utilits.extensions.default
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthViewModel(val context: Application) : AndroidViewModel(context) {

    private val TAG: String = this::class.java.simpleName
    private val state = MutableLiveData<StateAuth>().default(initialValue = StateAuth.Default)
    private val repository: AuthRepository = AuthRepositoryImpl()
    private val compositeDisposable = CompositeDisposable()

    fun auth(username: String, pass: String) {
        state.postValue(StateAuth.Sending)

        compositeDisposable.add(repository.auth(username, pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.warning == null) {
                    Log.d(TAG, "getToken: SUCCESS")
                    val pref: SharedPreferences =
                        context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = pref.edit()

                    editor.apply {
                        response.user?.id?.let { putInt(USER_ID, it) }
                        putString(USER_NAME, response.user?.first_name)
                        putString(USER_LAST_NAME, response.user?.last_name)
                        putString(USER_EMAIL, response.user?.email)
                        putStringSet(USER_GROUPS, response.user?.groups?.toSet())

                        putString(USER_TOKEN, response.token)
                    }
                    editor.apply()

                    state.postValue(StateAuth.Success("Success"))
                } else {
                    if (response.warning.codes.contains(WARNING_AUTH)) {
                        Log.d(TAG, "auth: Warning Auth")
                        when {
                            response.warning.codes.contains(WARNING_AUTH_USERNAME) -> state.postValue(StateAuth.UserDoesNotExist)
                            response.warning.codes.contains(WARNING_AUTH_PASSWORD) -> state.postValue(StateAuth.InvalidPass)
                        }
                    }
                }
            })
    }

    fun echo() {
        val pref: SharedPreferences =
            context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val t = "Token ${pref.getString(USER_TOKEN, "")}"

        ApiClient.client.create(ApiInterface::class.java)
            .echo(t)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $t")
                state.postValue(StateAuth.ActualToken)
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                state.postValue(StateAuth.OutdatedToken)
            })
    }

    fun getStates(): MutableLiveData<StateAuth> {
        return state
    }
}