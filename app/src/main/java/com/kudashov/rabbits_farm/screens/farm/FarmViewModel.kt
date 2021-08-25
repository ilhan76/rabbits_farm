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
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.messages.ERROR_NO_ITEM
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.*
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
    private val listOfSelectedCage: MutableList<CageDomain> = ArrayList()
    private val listOfRabbit: MutableList<RabbitDomain> = ArrayList()

    private var page: Int = 1
    private var maxPage: Int = 1
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
        if (page <= maxPage) {
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
                    maxPage = (response.first / pageSize) + 1
                    if (response.second.content != null) {
                        Log.d(TAG, "getRabbits: Success")
                        listOfRabbit.addAll(response.second.content!!)
                        state.postValue(StateFarm.SuccessRabbits(listOfRabbit))
                    } else {
                        Log.d(TAG, "getRabbits: Error")
                        when (response.second.detail) {
                            ERROR_NO_ITEM -> StateFarm.NoItem
                            else -> StateFarm.Error(response.second.detail)
                        }
                    }
                }
        }
    }

    fun getCages() {
        if (page <= maxPage) {
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
                    maxPage = (response.first / pageSize) + 1
                    if (response.second.content != null) {
                        Log.d(TAG, "getCages: Success")
                        listOfCage.addAll(response.second.content!!)
                        for (i in listOfCage){
                            for (j in listOfSelectedCage){
                                if (i.id == j.id) i.isSelected = j.isSelected
                            }
                        }
                        state.postValue(StateFarm.SuccessCage(listOfCage))
                    } else {
                        Log.d(TAG, "getCages: Error")
                        when (response.second.detail) {
                            ERROR_NO_ITEM -> StateFarm.NoItem
                            else -> StateFarm.Error(response.second.detail)
                        }
                    }
                }
        }
    }

    fun updateCageStatus(status: String) {
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        for (i in listOfSelectedCage) {
            when (status) {
                CAGE_STATUS_NEED_REPAIR, CAGE_STATUS_NEED_CLEAN ->
                    if (!i.statuses.contains(status))
                        i.statuses.add(status)

                CAGE_STATUS_CLEANED ->
                    if (i.statuses.contains(CAGE_STATUS_NEED_CLEAN))
                        i.statuses.remove(CAGE_STATUS_NEED_CLEAN)

                CAGE_STATUS_REPAIRED -> {
                    if (i.statuses.contains(CAGE_STATUS_NEED_REPAIR))
                        i.statuses.remove(CAGE_STATUS_NEED_REPAIR)
                    Log.d(TAG, "updateCageStatus: CAGE_STATUS_REPAIRED")
                }

            }
            Log.d(TAG, "updateCageStatus: ${i.statuses}")
            state.postValue(StateFarm.Sending)
            repository.updateCageStatus(token, i.id, i.statuses)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "updateCageStatus: Success")
                    state.postValue(StateFarm.Default)
                }, {
                    Log.d(TAG, "updateCageStatus: Error")
                    Log.d(TAG, "updateCageStatus: ${it.localizedMessage}")
                    state.postValue(StateFarm.Error(it.localizedMessage))
                })
        }
    }

    fun updateSelectedCages(cageDomain: CageDomain) {
        if (listOfSelectedCage.contains(cageDomain))
            listOfSelectedCage.remove(cageDomain)
        else
            listOfSelectedCage.add(cageDomain)
    }

    fun getStates(): MutableLiveData<StateFarm> {
        return state
    }
}