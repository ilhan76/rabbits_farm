package com.kudashov.rabbits_farm.screens.farm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.ui.CageItem
import com.kudashov.rabbits_farm.data.ui.RabbitItem
import com.kudashov.rabbits_farm.data.mapper.CageMapper
import com.kudashov.rabbits_farm.data.mapper.RabbitMapper
import com.kudashov.rabbits_farm.extensions.default
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryHeroku
import com.kudashov.rabbits_farm.screens.farm.filters.cage.CageFilter
import com.kudashov.rabbits_farm.screens.farm.filters.rabbit.RabbitFilter
import com.kudashov.rabbits_farm.utilits.StateAboutFarm
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.STATUSES_CAGE
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.TYPES_CAGE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class FarmViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String = this::class.java.simpleName
    private val repository: FarmRepository = FarmRepositoryHeroku()

    private val state = MutableLiveData<StateAboutFarm>().default(initialValue = StateAboutFarm.Default)

    private val listOfCage: MutableList<CageItem> = ArrayList()
    private val listOfRabbit: MutableList<RabbitItem> = ArrayList()

    private var page: Int = 1
    private val pageSize: Int = 50

    fun nextPage() {
        page++
    }

    fun cleanPage() {
        listOfRabbit.clear()
        listOfCage.clear()
        page = 1
    }

    fun getRabbits() {
        state.postValue(StateAboutFarm.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getRabbits(
            token,
            page,
            pageSize,
            RabbitFilter.farmNumber,
            RabbitFilter.type,
            RabbitFilter.breed,
            RabbitFilter.status,
            RabbitFilter.ageFrom,
            RabbitFilter.ageTo,
            RabbitFilter.cageNumberFrom,
            RabbitFilter.cageNumberTo,
            RabbitFilter.isMale,
            RabbitFilter.orderBy
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { rabbitServerResponse ->
                if (rabbitServerResponse.detail == null) {
                    Log.d(TAG, "getRabbits: SUCCESS")
                    listOfRabbit.addAll(RabbitMapper.fromApiToListRabbitItem(rabbitServerResponse.results!!))
                    state.postValue(StateAboutFarm.ListOfRabbitsReceived(listOfRabbit))
                } else {
                    Log.d(TAG, "getRabbits: ERROR ${rabbitServerResponse.detail}")
                    state.postValue(StateAboutFarm.Error("Error ${rabbitServerResponse.detail}"))
                }
            }
    }

    fun getCages() {
        state.postValue(StateAboutFarm.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getCages(
            token,
            page,
            pageSize,
            CageFilter.farmNumber,
            CageFilter.status,
            CageFilter.type,
            CageFilter.isParallel,
            CageFilter.countOfRabbitFrom,
            CageFilter.countOfRabbitTo,
            CageFilter.orderBy
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { cageServerResponse ->
                if (cageServerResponse.detail == null) {
                    Log.i(TAG, "getRabbits: SUCCESS")
                    listOfCage.addAll(CageMapper.fromApiToListCageItem(cageServerResponse.results!!))
                    state.postValue(StateAboutFarm.ListOfCageReceived(listOfCage))
                } else {
                    Log.i(TAG, "getCages: ERROR ${cageServerResponse.detail}")
                    state.postValue(StateAboutFarm.Error("Error ${cageServerResponse.detail}"))
                }
            }
    }

    fun getStates(): MutableLiveData<StateAboutFarm> {
        return state
    }

    fun getListOfStatuses(): List<String> {
        val list: MutableList<String> = ArrayList()
        list.add("")
        for (i in STATUSES_CAGE) {
            list.add(i.second)
        }
        return list
    }

    fun getListOfTypes(): List<String> {
        val list: MutableList<String> = ArrayList()
        list.add("")
        for (i in TYPES_CAGE) {
            list.add(i.second)
        }
        return list
    }
}