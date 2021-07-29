package com.kudashov.rabbits_farm.screens.birth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.repository.implementation.DataRepositoryTest
import com.kudashov.rabbits_farm.utilits.StateBirth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BirthViewModel(application: Application): AndroidViewModel(application) {
    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateBirth> = MutableLiveData()
    private val repository: DataRepository = DataRepositoryTest()

    fun getTasks(isConfirmed: Boolean){
        state.postValue(StateBirth.Sending)

        repository.getBirth(isConfirmed)
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