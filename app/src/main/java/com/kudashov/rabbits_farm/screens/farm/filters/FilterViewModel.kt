package com.kudashov.rabbits_farm.screens.farm.filters

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.converters.implementation.FarmConverterImpl
import com.kudashov.rabbits_farm.data.source.implementation.FarmProviderImpl
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryImpl
import com.kudashov.rabbits_farm.utilits.BaseState
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.STATUSES_CAGE
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.TYPES_CAGE
import com.kudashov.rabbits_farm.utilits.extensions.default
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FilterViewModel(val context: Application) : AndroidViewModel(context) {

    private val TAG: String = this::class.java.simpleName
    private val repository: FarmRepository = FarmRepositoryImpl(
        converter = FarmConverterImpl(),
        provider = FarmProviderImpl()
    )
    private val _stateBreed = MutableLiveData<BaseState>().default(BaseState.Default)
    val stateBreed: LiveData<BaseState> = _stateBreed

    private val _statuses = MutableLiveData<List<String>>()
    val statuses: LiveData<List<String>> = _statuses

    private val _types = MutableLiveData<List<String>>()
    val types: LiveData<List<String>> = _statuses

    fun getBreed() {
        _stateBreed.postValue(BaseState.Sending)

        val t = "Token ${
            context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
                .getString(USER_TOKEN, "")
        }"

        repository.getBreed(t)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getBreed: Success")
                _stateBreed.postValue(BaseState.Success(it.content))
            }, {
                Log.d(TAG, "getBreed: Error ${it.localizedMessage}")
                Log.d(TAG, "getBreed: Error ${it.stackTrace}")
                _stateBreed.postValue(BaseState.Error(it.localizedMessage))
            })
    }

    fun getListOfStatuses() {
        val list: MutableList<String> = ArrayList()
        list.add("")
        for (i in STATUSES_CAGE) {
            list.add(i.second)
        }
        _statuses.postValue(list)
    }

    fun getListOfTypes() {
        val list: MutableList<String> = ArrayList()
        list.add("")
        for (i in TYPES_CAGE) {
            list.add(i.second)
        }
        _types.postValue(list)
    }
}