package com.kudashov.rabbits_farm.screens.farmMenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

sealed class StateAboutFarmMenu {
    class Default: StateAboutFarmMenu()
    class Sending: StateAboutFarmMenu()
    class ListReceivedSuccessfully<T>(val list: List<T>): StateAboutFarmMenu()
    class Error<T>(val message: T): StateAboutFarmMenu()
}

class AboutFarmMenuViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateAboutFarmMenu> = MutableLiveData()

    fun provideTasks(){
        state.postValue(StateAboutFarmMenu.Sending())
        // todo - запрос к базе
    }

    fun getStates(): MutableLiveData<StateAboutFarmMenu> {
        return state
    }
}