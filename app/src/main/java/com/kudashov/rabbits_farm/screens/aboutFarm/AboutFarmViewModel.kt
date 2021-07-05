package com.kudashov.rabbits_farm.screens.aboutFarm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class State {
    class Default: State()
    class Sending: State()
    class ListReceivedSuccessfully<T>(val list: List<T>): State()
    class Error<T>(val message: T): State()
}

class AboutFarmViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<State> = MutableLiveData()

    //todo добавить фильтры
    fun getRabbits(){
        state.postValue(State.Sending())
        // todo - запрос к серверу на получение кроликов
    }


    fun getStates(): MutableLiveData<State> {
        return state
    }
}