package com.kudashov.rabbits_farm.screens.farm.dialog

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.converters.implementation.FarmConverterImpl
import com.kudashov.rabbits_farm.data.source.implementation.FarmProviderImpl
import com.kudashov.rabbits_farm.repository.FarmRepository
import com.kudashov.rabbits_farm.repository.implementation.FarmRepositoryImpl
import com.kudashov.rabbits_farm.utilits.*
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*
import com.kudashov.rabbits_farm.utilits.extensions.default
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class RabbitViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String? = RabbitViewModel::class.java.simpleName

    private val repository: FarmRepository = FarmRepositoryImpl(
        converter = FarmConverterImpl(),
        provider = FarmProviderImpl()
    )
    private val pref: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

    private val _rabbitState =
        MutableLiveData<BaseState>().default(initialValue = BaseState.Default)
    val rabbitState: LiveData<BaseState> = _rabbitState

    private val _operationState =
        MutableLiveData<BaseListState>().default(initialValue = BaseListState.Default)
    val operationState: LiveData<BaseListState> = _operationState

    private val _isRecastState =
        MutableLiveData<RecastState>().default(initialValue = RecastState.Default)
    val isRecastState: MutableLiveData<RecastState> = _isRecastState

    private var isRecast: Boolean? = false

    fun getRabbitMoreInf(id: Int) {
        _rabbitState.postValue(BaseState.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.content != null) {
                    Log.d(TAG, "getRabbitMoreInf: Success")
                    _rabbitState.postValue(BaseState.Success(response.content))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: Error")
                    when (response.detail) {
                        else -> _rabbitState.postValue(BaseState.Error(response.detail))
                    }
                }
            }
    }

    fun getOperations(id: Int) {
        _operationState.postValue(BaseListState.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.content != null) {
                    Log.d(TAG, "getRabbitMoreInf: Success")
                    _operationState.postValue(BaseListState.Success(response.content))
                } else {
                    Log.d(TAG, "getRabbitMoreInf: Error")
                    when (response.detail) {
                        ERROR_NO_ITEM -> StateFarm.NoItem
                        else -> _operationState.postValue(BaseListState.Error(response.detail))
                    }
                }
            }
    }

    fun isRecast(id: Int, type: String) {
        _isRecastState.postValue(RecastState.Sending)

        if (type == RABBIT_TYPE_UI_BABY) {
            _isRecastState.postValue(RecastState.Bunny)
        } else {
            val token = "Token ${pref.getString(USER_TOKEN, "")}"
            repository.isRecast(token, getPathType(type), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "isRecast: IsRecast")
                    isRecast = it.content
                    _isRecastState.postValue(RecastState.IsRecast(it.content!!))
                }, {
                    Log.d(TAG, "isRecast: Error")
                    Log.d(TAG, "isRecast: ${it.stackTrace}")
                    _isRecastState.postValue(RecastState.Error(it.localizedMessage))
                })
        }
    }

    fun createRecast(id: Int, type: String) {
        _isRecastState.postValue(RecastState.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"
        repository.createRecast(token, getPathType(type), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "isRecast: Success")
                _isRecastState.postValue(RecastState.Default)
            }, {
                Log.d(TAG, "isRecast: Error")
                Log.d(TAG, "isRecast: ${it.stackTrace}")
                _isRecastState.postValue(RecastState.Error(it.localizedMessage))
            })
    }

    fun deleteRecast(id: Int, type: String) {
        _isRecastState.postValue(RecastState.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"
        repository.deleteRecast(token, getPathType(type), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "isRecast: Success")
                _isRecastState.postValue(RecastState.Success)
            }, {
                Log.d(TAG, "isRecast: Error")
                Log.d(TAG, "isRecast: ${it.stackTrace}")
                _isRecastState.postValue(RecastState.Error(it.localizedMessage))
            })
    }

    fun postWeight(weight: Double, type: String, id: Int) {
        _rabbitState.postValue(BaseState.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.postWeight(token, weight = weight, id = id, pathType = getPathType(type))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "postWeight: Success")
                _rabbitState.postValue(BaseState.Default)
            }, {
                Log.d(TAG, "postWeight: Error")
                _rabbitState.postValue(BaseState.Error(it.localizedMessage))
            })
    }

    private fun getPathType(type: String): String {
        return when (type) {
            RABBIT_TYPE_UI_BABY -> RABBIT_TYPE_PATH_BABY
            RABBIT_TYPE_UI_MATHER -> RABBIT_TYPE_PATH_MATHER
            RABBIT_TYPE_UI_FATHER -> RABBIT_TYPE_PATH_FATHER
            RABBIT_TYPE_UI_FATTENING -> RABBIT_TYPE_PATH_FATTENING
            else -> ""
        }
    }
}