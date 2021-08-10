package com.kudashov.rabbits_farm.screens.farm.dialog

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.converters.OperationMapper
import com.kudashov.rabbits_farm.data.converters.RabbitMapper
import com.kudashov.rabbits_farm.utilits.extensions.default
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateRabbit
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class RabbitViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String? = RabbitViewModel::class.java.simpleName
    private val state = MutableLiveData<StateRabbit>().default(initialValue = StateRabbit.Default)
    private val repository: FarmRepository = FarmRepositoryHeroku()

    private var rabbitDto: RabbitMoreInfDto? = null

    fun getStates(): MutableLiveData<StateRabbit> {
        return state
    }

    fun getRabbitMoreInf(id: Int) {
        state.postValue(StateRabbit.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.detail != null && it.detail.isEmpty()) {
                    Log.d(TAG, "getRabbitMoreInf: SUCCESS")
                    rabbitDto = it.rabbit
                    state.postValue(StateRabbit.SuccessRabbit(RabbitMapper.fromApiToRabbitDomain(it.rabbit!!)))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: ERROR ${it.detail}")
                    state.postValue(StateRabbit.Error(it.detail))
                }
            }
    }

    fun getOperations(id: Int) {
        state.postValue(StateRabbit.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.detail == null || it.detail.isEmpty()) {
                    Log.d(TAG, "getOperations: SUCCESS")
                    state.postValue(
                        StateRabbit.SuccessOperations(
                            OperationMapper.fromApiToListItem(
                                it.results!!
                            )
                        )
                    )
                } else {
                    Log.d(TAG, "getOperations: ERROR ${it.detail}")
                    state.postValue(StateRabbit.Error(it.detail))
                }
            }
    }

    fun postWeight(weight: Double, type: String, id: Int) {
        state.postValue(StateRabbit.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
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
            .subscribe {
                if (it.detail == null || it.detail.isEmpty()) {
                    Log.d(TAG, "postWeight: Success")
                    state.postValue(StateRabbit.Default)
                } else {
                    Log.d(TAG, "getOperations: Error ${it.detail}")
                    state.postValue(StateRabbit.Error(it.detail))
                }
            }
    }
}