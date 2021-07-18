package com.kudashov.rabbits_farm.screens.dialogs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.Operation
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.repository.implementation.DataRepositoryTest
import com.kudashov.rabbits_farm.utilits.StateRabbit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class RabbitViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String? = RabbitViewModel::class.java.simpleName
    private val state: MutableLiveData<StateRabbit> = MutableLiveData()
    private val repository: DataRepository = DataRepositoryTest()

    fun getStates(): MutableLiveData<StateRabbit>{
        Log.d(TAG, "getStates: ")
        return state
    }

    fun getOperations(){
        state.postValue(StateRabbit.Sending())

        repository.getOperations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.respError?.isEmpty()!!){
                        Log.d(TAG, "getOperations: SUCCESS")
                        state.postValue(StateRabbit.Success(it.list))
                    } else {
                        Log.d(TAG, "getOperations: ERROR")
                        state.postValue(StateRabbit.Error("ERROR"))
                    }
                }
    }
}