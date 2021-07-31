package com.kudashov.rabbits_farm.screens.birth

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.mapper.BirthMapper
import com.kudashov.rabbits_farm.data.ui.BirthListItem
import com.kudashov.rabbits_farm.extensions.default
import com.kudashov.rabbits_farm.repository.BirthRepository
import com.kudashov.rabbits_farm.repository.implementation.BirthRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateBirth
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BirthViewModel(val context: Application) : AndroidViewModel(context) {
    private val TAG: String = this::class.java.simpleName

    private val state = MutableLiveData<StateBirth>().default(initialValue = StateBirth.Default)
    private val repository: BirthRepository = BirthRepositoryHeroku()

    private val listOfBirth: MutableList<BirthListItem> = ArrayList()

    private var page: Int = 1
    private val pageSize: Int = 50

    fun nextPage() {
        page++
    }

    fun cleanPage() {
        listOfBirth.clear()
        page = 1
    }

    fun getBirth(isConfirmed: Boolean, orderBy: String?) {
        state.postValue(StateBirth.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getBirth(token, isConfirmed, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.detail == null || response.detail.isEmpty()) {
                    Log.i(TAG, "getRabbits: SUCCESS")
                    state.postValue(
                        StateBirth.ListOfBirthReceived(
                            BirthMapper.fromApiToListItem(
                                response.results!!
                            )
                        )
                    )
                } else {
                    Log.i(TAG, "getRabbits: ERROR")
                    state.postValue(StateBirth.Error(response.detail))
                }
            }
    }


    fun getStates(): MutableLiveData<StateBirth> {
        return state
    }
}