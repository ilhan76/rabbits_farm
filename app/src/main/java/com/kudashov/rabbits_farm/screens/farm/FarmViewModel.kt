package com.kudashov.rabbits_farm.screens.farm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.mapper.CageMapper
import com.kudashov.rabbits_farm.data.mapper.RabbitMapper
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.repository.implementation.DataRepositoryHeroku
import com.kudashov.rabbits_farm.screens.farm.filters.RabbitFilter
import com.kudashov.rabbits_farm.utilits.StateAboutFarm
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class FarmViewModel(application: Application) : AndroidViewModel(application), Serializable {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateAboutFarm> = MutableLiveData()
    private val repository: DataRepository = DataRepositoryHeroku()

    fun getRabbits() {
        state.postValue(StateAboutFarm.Sending)

        repository.getRabbits(
            RabbitFilter.page,
            RabbitFilter.pageSize,
            RabbitFilter.farmNumber,
            RabbitFilter.type,
            RabbitFilter.breed,
            RabbitFilter.status,
            RabbitFilter.ageFrom,
            RabbitFilter.ageTo,
            RabbitFilter.cageNumberFrom,
            RabbitFilter.cageNumberTo,
            RabbitFilter.isMale,
            RabbitFilter.orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { rabbitServerResponse ->
                if (rabbitServerResponse.detail == null) {
                    Log.d(TAG, "getRabbits: SUCCESS")
                    state.postValue(
                        StateAboutFarm.ListOfRabbitsReceived(
                            RabbitMapper.fromApiToListRabbitItem(
                                rabbitServerResponse.results!!
                            )
                        )
                    )
                } else {
                    Log.d(TAG, "getRabbits: ERROR ${rabbitServerResponse.detail}")
                    state.postValue(StateAboutFarm.Error("Error ${rabbitServerResponse.detail}"))
                }
            }
    }

    //todo добавить фильтры для клеток
    fun getCages() {
        state.postValue(StateAboutFarm.Sending)

        repository.getCages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { cageServerResponse ->
                if (cageServerResponse.detail == null) {
                    Log.i(TAG, "getRabbits: SUCCESS")
                    state.postValue(
                        StateAboutFarm.ListOfCageReceived(
                            CageMapper.fromApiToListCageItem(
                                cageServerResponse.results!!
                            )
                        )
                    )
                } else {
                    Log.i(TAG, "getCages: ERROR ${cageServerResponse.detail}")
                    state.postValue(StateAboutFarm.Error("Error ${cageServerResponse.detail}"))
                }
            }
    }

    fun getStates(): MutableLiveData<StateAboutFarm> {
        return state
    }
}