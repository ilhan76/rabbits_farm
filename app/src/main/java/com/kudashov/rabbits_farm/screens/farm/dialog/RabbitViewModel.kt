package com.kudashov.rabbits_farm.screens.farm.dialog

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateRabbit
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.STATUSES_RABBIT
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RabbitViewModel(val context: Application) : AndroidViewModel(context) {

    private val TAG: String? = RabbitViewModel::class.java.simpleName
    private val state: MutableLiveData<StateRabbit> = MutableLiveData()
    private val repository: FarmRepository = FarmRepositoryHeroku()

    fun getStates(): MutableLiveData<StateRabbit> {
        return state
    }

    fun getRabbitMoreInf(id: Int) {
        state.postValue(StateRabbit.Sending)

        repository.getRabbitMoreInf(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.detail != null && it.detail.isEmpty()) {
                    Log.d(TAG, "getRabbitMoreInf: SUCCESS")
                    state.postValue(StateRabbit.SuccessRabbit(it.rabbit!!))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: ERROR ${it.detail}")
                    state.postValue(StateRabbit.Error(it.detail))
                }
            }
    }

    fun getOperations(id: Int) {
        state.postValue(StateRabbit.Sending)

        repository.getOperations(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.detail == null || it.detail.isEmpty()) {
                    Log.d(TAG, "getOperations: SUCCESS")
                    state.postValue(StateRabbit.SuccessOperations(it.results!!))
                } else {
                    Log.d(TAG, "getOperations: ERROR ${it.detail}")
                    state.postValue(StateRabbit.Error(it.detail))
                }
            }
    }

    fun postWeight(weight: Double, type: String, id: Int) {
        state.postValue(StateRabbit.Sending)
    }

    fun getStatus(statuses: List<String>): String {
        var res: String = ""
        for (i in STATUSES_RABBIT){
            if (statuses.contains(i.first)) res += i.second + "\n"
        }
        if (res.isEmpty()) res+ "Статус не определен"
        return res
    }
}