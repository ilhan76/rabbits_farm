package com.kudashov.rabbits_farm.screens.birth

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.converters.implementation.BirthConverterImpl
import com.kudashov.rabbits_farm.data.domain.BirthDomain
import com.kudashov.rabbits_farm.data.source.implementation.BirthProviderHeroku
import com.kudashov.rabbits_farm.utilits.extensions.default
import com.kudashov.rabbits_farm.net.request.birth.ConfirmPregnancyRequest
import com.kudashov.rabbits_farm.net.request.birth.TakeBirthRequest
import com.kudashov.rabbits_farm.repository.BirthRepository
import com.kudashov.rabbits_farm.repository.implementation.BirthRepositoryImpl
import com.kudashov.rabbits_farm.utilits.StateBirth
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import com.kudashov.rabbits_farm.utilits.const.messages.ERROR_NO_ITEM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class BirthViewModel(val context: Application) : AndroidViewModel(context), Serializable {
    private val TAG: String = this::class.java.simpleName

    private val state = MutableLiveData<StateBirth>().default(initialValue = StateBirth.Default)
    private val repository: BirthRepository = BirthRepositoryImpl(
        converter = BirthConverterImpl(),
        provider = BirthProviderHeroku()
    )

    private val listOfBirth: MutableList<BirthDomain> = ArrayList()

    private var page: Int = 1
    private val pageSize: Int = 50
    private var maxPage = 1
    private var orderBy: String? = null

    fun setOrderBy(orderBy: String?){
        this.orderBy = orderBy
        state.postValue(StateBirth.Default)
    }

    fun nextPage() {
        page++
    }

    fun cleanPage() {
        listOfBirth.clear()
        page = 1
    }

    fun getBirth(isConfirmed: Boolean) {
        if (page <= maxPage) {
            state.postValue(StateBirth.Sending)

            val pref: SharedPreferences = context.getSharedPreferences(
                APP_PREFERENCE, Context.MODE_PRIVATE
            )
            val token = "Token ${pref.getString(USER_TOKEN, "")}"

            repository.getBirth(
                token,
                page,
                pageSize,
                isConfirmed,
                orderBy
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    maxPage = response.first / pageSize + 1
                    if (response.second.content != null) {
                        Log.d(TAG, "getBirth: Success")
                        state.postValue(StateBirth.SuccessBirth(response.second.content!!))
                    } else {
                        Log.d(TAG, "getBirth: Error")
                        when (response.second.detail) {
                            ERROR_NO_ITEM -> state.postValue(StateBirth.NoItem)
                            else -> state.postValue(StateBirth.Error("Unknown error ${response.second.detail}"))
                        }
                    }
                }
        }
    }

    fun confirmPregnancy(id: Int, isConfirmed: Boolean) {
        state.postValue(StateBirth.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.confirmPregnancy(token, id, ConfirmPregnancyRequest(isConfirmed))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.detail == null || response.detail.isEmpty()) {
                    Log.d(TAG, "confirmPregnancy: Success")
                    state.postValue(StateBirth.Default)
                } else {
                    Log.d(TAG, "confirmPregnancy: ${response.detail}")
                    state.postValue(StateBirth.Error(response.detail))
                }
            }
    }

    fun takeBirth(id: Int, bornBunnies: Int) {
        state.postValue(StateBirth.Sending)

        val pref: SharedPreferences = context.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE
        )
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        repository.takeBirth(token, id, TakeBirthRequest(bornBunnies))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.detail == null || response.detail.isEmpty()) {
                    Log.d(TAG, "takeBirth: Success")
                    state.postValue(StateBirth.Default)
                } else {
                    Log.d(TAG, "takeBirth: ${response.detail}")
                    state.postValue(StateBirth.Error(response.detail))
                }
            }
    }

    fun getStates(): MutableLiveData<StateBirth> {
        return state
    }
}