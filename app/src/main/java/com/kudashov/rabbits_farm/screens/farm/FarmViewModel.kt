package com.kudashov.rabbits_farm.screens.farm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.Cage
import com.kudashov.rabbits_farm.data.Rabbit
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.repository.implementation.DataRepositoryTest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


sealed class StateAboutFarm {
    class Default: StateAboutFarm()
    class Sending: StateAboutFarm()
    class ListOfRabbitsReceived(val list: List<Rabbit>): StateAboutFarm()
    class ListOfCageReceived(val list: List<Cage>): StateAboutFarm()
    class Error<T>(val message: T): StateAboutFarm()
}

class AboutFarmViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateAboutFarm> = MutableLiveData()
    private val repository: DataRepository = DataRepositoryTest()

    //todo добавить фильтры
    fun getRabbits(){
        state.postValue(StateAboutFarm.Sending())

        repository.getRabbits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { rabbitServerResponse ->
                    if (rabbitServerResponse.respError?.isEmpty()!!){
                        Log.i(TAG, "getRabbits: SUCCESS")
                        state.postValue(StateAboutFarm.ListOfRabbitsReceived(rabbitServerResponse.rabbits!!))
                    } else {
                        Log.i(TAG, "getRabbits: ERROR")
                        state.postValue(StateAboutFarm.Error("Error"))
                    }
                }
    }

    fun getCages(){
        state.postValue(StateAboutFarm.Sending())

        repository.getCages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cageServerResponse ->
                    if (cageServerResponse.respError?.isEmpty()!!){
                        Log.i(TAG, "getRabbits: SUCCESS")
                        state.postValue(StateAboutFarm.ListOfCageReceived(cageServerResponse.cages))
                    } else {
                        Log.i(TAG, "getRabbits: ERROR")
                        state.postValue(StateAboutFarm.Error("Error"))
                    }
                }
    }


    fun getStates(): MutableLiveData<StateAboutFarm> {
        return state
    }
}