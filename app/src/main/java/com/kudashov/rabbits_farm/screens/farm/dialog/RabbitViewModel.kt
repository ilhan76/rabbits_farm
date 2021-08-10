package com.kudashov.rabbits_farm.screens.farm.dialog

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.converters.implementation.FarmConverterImpl
import com.kudashov.rabbits_farm.data.source.implementation.FarmProviderImpl
import com.kudashov.rabbits_farm.utilits.extensions.default
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryImpl
import com.kudashov.rabbits_farm.utilits.StateFarm
import com.kudashov.rabbits_farm.utilits.StateRabbit
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class RabbitViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String? = RabbitViewModel::class.java.simpleName
    private val state = MutableLiveData<StateRabbit>().default(initialValue = StateRabbit.Default)
    private val repository: FarmRepository = FarmRepositoryImpl(
        converter = FarmConverterImpl(),
        provider = FarmProviderImpl()
    )

    private val pref: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCE, Context.MODE_PRIVATE
    )

    fun getStates(): MutableLiveData<StateRabbit> {
        return state
    }

    fun getRabbitMoreInf(id: Int) {
        state.postValue(StateRabbit.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.content != null) {
                    Log.d(TAG, "getRabbitMoreInf: Success")

                    state.postValue(StateRabbit.SuccessRabbit(response.content))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: Error")
                    when (response.detail) {
                        ERROR_NO_ITEM -> StateFarm.NoItem
                        else -> StateFarm.Error(response.detail)
                    }
                }
            }
    }

    fun getOperations(id: Int) {
        state.postValue(StateRabbit.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.content != null) {
                    Log.d(TAG, "getRabbitMoreInf: Success")
                    state.postValue(StateRabbit.SuccessOperations(response.content))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: Error")
                    when (response.detail) {
                        ERROR_NO_ITEM -> StateFarm.NoItem
                        else -> StateFarm.Error(response.detail)
                    }
                }
            }
    }

    fun postWeight(weight: Double, type: String, id: Int) {
        state.postValue(StateRabbit.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        var pathType = ""
        when (type) {
            RABBIT_TYPE_BABY -> pathType = RABBIT_TYPE_PATH_BABY
            RABBIT_TYPE_MATHER -> pathType = RABBIT_TYPE_PATH_MATHER
            RABBIT_TYPE_FATHER -> pathType = RABBIT_TYPE_PATH_FATHER
            RABBIT_TYPE_FATTENING -> pathType = RABBIT_TYPE_PATH_FATTENING
        }

        repository.postWeight(token, weight = weight, id = id, pathType = pathType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "postWeight: Success")
                state.postValue(StateRabbit.Default)
            }, {
                Log.d(TAG, "postWeight: Error")
                state.postValue(StateRabbit.Error(it.localizedMessage))
            })
    }
}