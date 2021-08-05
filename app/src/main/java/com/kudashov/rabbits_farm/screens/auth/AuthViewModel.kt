package com.kudashov.rabbits_farm.screens.auth

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.response.EchoResponse
import com.kudashov.rabbits_farm.repository.AuthRepository
import com.kudashov.rabbits_farm.repository.implementation.AuthRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.*
import com.kudashov.rabbits_farm.utilits.const.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthViewModel(val context: Application) : AndroidViewModel(context) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateAuth> = MutableLiveData()
    private val repository: AuthRepository = AuthRepositoryHeroku()
    private val compositeDisposable = CompositeDisposable()

    fun auth(username: String, pass: String) {
        state.postValue(StateAuth.Sending)

        compositeDisposable.add(repository.auth(username, pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.detail == null) {
                    Log.d(TAG, "getToken: SUCCESS")
                    val pref: SharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = pref.edit()

                    editor.apply {
                        putString(USER_NAME, response.user?.first_name)
                        putString(USER_LAST_NAME, response.user?.last_name)
                        putString(USER_EMAIL, response.user?.email)
                        putStringSet(USER_GROUPS, response.user?.groups?.toSet())

                        putString(USER_TOKEN, response.token)
                    }
                    editor.apply()

                    state.postValue(StateAuth.Success("Success"))
                } else {
                    Log.d(TAG, "getToken: ERROR ${response.detail}")
                    state.postValue(StateAuth.Error("Error ${response.detail}"))
                }
            })
    }

    fun echo(){
        val pref: SharedPreferences = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val t = "Token ${pref.getString(USER_TOKEN, "")}"

        ApiClient.client.create(ApiInterface::class.java)
            .echo(t)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: $t")
                Toast.makeText(context, it?.detail, Toast.LENGTH_SHORT).show()
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                Toast.makeText(context, it?.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }

    fun getStates(): MutableLiveData<StateAuth> {
        return state
    }
}