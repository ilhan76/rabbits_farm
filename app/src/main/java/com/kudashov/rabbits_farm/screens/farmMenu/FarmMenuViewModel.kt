package com.kudashov.rabbits_farm.screens.farmMenu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.utilits.StateAboutFarmMenu

class FarmMenuViewModel(application: Application): AndroidViewModel(application) {

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