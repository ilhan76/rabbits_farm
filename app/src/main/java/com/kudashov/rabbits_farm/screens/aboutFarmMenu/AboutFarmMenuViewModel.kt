package com.kudashov.rabbits_farm.screens.aboutFarmMenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

sealed class State {
    class Default: State()
    class Sending: State()
    class ListReceivedSuccessfully<T>(val list: List<T>): State()
    class Error<T>(val message: T): State()
}

class AboutFarmMenuViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<State> = MutableLiveData()

    fun provideTasks(){
        state.postValue(State.Sending())
        // todo - запрос к базе
    }

    fun getStates(): MutableLiveData<State> {
        return state
    }
}