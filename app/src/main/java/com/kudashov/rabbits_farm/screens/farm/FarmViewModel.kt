package com.kudashov.rabbits_farm.screens.farm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.domain.CageDomain
import com.kudashov.rabbits_farm.data.domain.RabbitDomain
import com.kudashov.rabbits_farm.data.converters.implementation.FarmConverterImpl
import com.kudashov.rabbits_farm.data.source.implementation.FarmProviderImpl
import com.kudashov.rabbits_farm.utilits.extensions.default
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryImpl
import com.kudashov.rabbits_farm.screens.farm.filters.cage.CageFilter
import com.kudashov.rabbits_farm.screens.farm.filters.rabbit.RabbitFilter
import com.kudashov.rabbits_farm.utilits.StateFarm
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.STATUSES_CAGE
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.TYPES_CAGE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class FarmViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String = this::class.java.simpleName
    private val repository: FarmRepository = FarmRepositoryImpl(
        converter = FarmConverterImpl(),
        provider = FarmProviderImpl()
    )
    private val pref: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCE, Context.MODE_PRIVATE
    )

    private val state =
        MutableLiveData<StateFarm>().default(initialValue = StateFarm.Default)

    private val listOfCage: MutableList<CageDomain> = ArrayList()
    private val listOfRabbit: MutableList<RabbitDomain> = ArrayList()

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
        state.postValue(StateFarm.Sending)

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
            .subscribe { response ->
                if (response.content != null){
                    Log.d(TAG, "getRabbits: Success")
                    state.postValue(StateFarm.SuccessRabbits(response.content))
                } else {
                    Log.d(TAG, "getRabbits: Error")
                    when(response.detail){
                        ERROR_NO_ITEM -> StateFarm.NoItem
                        else -> StateFarm.Error(response.detail)
                    }
                }
            }
    }

    fun getCages() {
        state.postValue(StateFarm.Sending)

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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.content != null){
                    Log.d(TAG, "getCages: Success")
                    state.postValue(StateFarm.SuccessCage(response.content))
                } else {
                    Log.d(TAG, "getCages: Error")
                    when(response.detail){
                        ERROR_NO_ITEM -> StateFarm.NoItem
                        else -> StateFarm.Error(response.detail)
                    }
                }
            }
    }

    fun getStates(): MutableLiveData<StateFarm> {
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