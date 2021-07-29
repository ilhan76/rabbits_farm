package com.kudashov.rabbits_farm.screens.birth

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.repository.BirthRepository
import com.kudashov.rabbits_farm.repository.implementation.BirthRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateBirth
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BirthViewModel(val context: Application): AndroidViewModel(context) {
    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateBirth> = MutableLiveData()
    private val repository: BirthRepository = BirthRepositoryHeroku()

    fun getTasks(isConfirmed: Boolean){
        state.postValue(StateBirth.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getBirth(token, isConfirmed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.detail?.isEmpty()!!){
                        Log.i(TAG, "getRabbits: SUCCESS")
                        state.postValue(StateBirth.ListOfBirthReceived(response.list))
                    } else {
                        Log.i(TAG, "getRabbits: ERROR")
                        state.postValue(StateBirth.Error("Error"))
                    }
                }
    }


    fun getStates(): MutableLiveData<StateBirth> {
        return state
    }
}