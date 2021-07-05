package com.kudashov.rabbits_farm.screens.aboutFarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

sealed class StateAboutFarm {
    class Default: StateAboutFarm()
    class Sending: StateAboutFarm()
    class ListReceivedSuccessfully<T>(val list: List<T>): StateAboutFarm()
    class Error<T>(val message: T): StateAboutFarm()
}

class AboutFarmViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateAboutFarm> = MutableLiveData()

    //todo добавить фильтры
    fun getRabbits(){
        state.postValue(StateAboutFarm.Sending())
        // todo - запрос к серверу на получение кроликов
    }


    fun getStates(): MutableLiveData<StateAboutFarm> {
        return state
    }
}