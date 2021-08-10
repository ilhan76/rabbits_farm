package com.kudashov.rabbits_farm.screens.tasks

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.data.converters.implementation.TaskConverterImpl
import com.kudashov.rabbits_farm.data.domain.TaskListItemType
import com.kudashov.rabbits_farm.data.source.implementation.TaskProviderHeroku
import com.kudashov.rabbits_farm.utilits.extensions.default
import com.kudashov.rabbits_farm.repository.TaskRepository
import com.kudashov.rabbits_farm.repository.implementation.TaskRepositoryImpl
import com.kudashov.rabbits_farm.utilits.StateTask
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.ERROR_NO_ITEM
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class TasksViewModel(val context: Application) : AndroidViewModel(context), Serializable,
    TaskDelegate {

    private val TAG: String = this::class.java.simpleName
    private val state = MutableLiveData<StateTask>().default(initialValue = StateTask.Default)
    private val repository: TaskRepository = TaskRepositoryImpl(
        converter = TaskConverterImpl(),
        provider = TaskProviderHeroku()
    )
    private val compositeDisposable = CompositeDisposable()
    private val pref = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

    private var deathCause: String? = null
    private val listOfTask: MutableList<TaskListItemType> = ArrayList()
    private var page: Int = 1
    private val pageSize: Int = 50

    fun nextPage() {
        page++
    }

    fun cleanPage() {
        listOfTask.clear()
        page = 1
    }

    fun setDeathCause(cause: String?) {
        deathCause = cause
    }

    fun getTasks(isDone: Boolean, orderBy: String?) {
        state.postValue(StateTask.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        compositeDisposable.add(
            repository.getTasks(token, isDone, page, pageSize, orderBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.content != null) {
                        Log.i(TAG, "getRabbits: SUCCESS")
                        state.postValue(StateTask.ListOfTaskReceived(response.content))
                    } else {
                        Log.d(TAG, "getTasks: Error")
                        when (response.detail) {
                            ERROR_NO_ITEM -> state.postValue(StateTask.NoItem)
                            else -> StateTask.Error("Unknown error ${response.detail}")
                        }
                    }
                })
    }

    fun putDeath(farmNumber: Int, cageNumber: Int, letter: String) {
        state.postValue(StateTask.Sending)

        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        compositeDisposable.add(
            repository.putDeath(token, farmNumber, cageNumber, letter, deathCause)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "putDeath: Success")
                    state.postValue(StateTask.Default)
                }, {
                    Log.d(TAG, "putDeath: Error")
                    state.postValue(StateTask.Error(it.localizedMessage))
                })
        )
    }

    override fun confirmSimpleTask(id: Int) {
        state.postValue(StateTask.Sending)

        compositeDisposable.add(
            repository.confirmSimpleTask(
                token = "Token ${pref.getString(USER_TOKEN, "")}",
                id = id
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "confirmSimpleTask: Success")
                    state.postValue(StateTask.Default)
                }, {
                    Log.d(TAG, "confirmSimpleTask: Error")
                    state.postValue(StateTask.Error(it.localizedMessage))
                })
        )
    }

    override fun confirmSlaughterInspectionTask(id: Int, weights: List<Double>) {
        if (weights.isEmpty()) {
            state.postValue(StateTask.NotAllFieldsAreFilledIn)
        } else{
            state.postValue(StateTask.Sending)

            compositeDisposable.add(
                repository.confirmSlaughterInspectionTask(
                    token = "Token ${pref.getString(USER_TOKEN, "")}",
                    id = id,
                    weights = weights
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "confirmSlaughterInspectionTask: Success")
                        state.postValue(StateTask.Default)
                    }, {
                        Log.d(TAG, "confirmSlaughterInspectionTask: Error")
                        state.postValue(StateTask.Error(it.localizedMessage))
                    })
            )
        }
    }

    override fun confirmDepositionFromMotherTask(id: Int, countMales: Int) {
        state.postValue(StateTask.Sending)

        compositeDisposable.add(
            repository.confirmDepositionFromMotherTask(
                token = "Token ${pref.getString(USER_TOKEN, "")}",
                id = id,
                countMales = countMales
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "confirmDepositionFromMotherTask: Success")
                    state.postValue(StateTask.Default)
                }, {
                    Log.d(TAG, "confirmDepositionFromMotherTask: Error")
                    state.postValue(StateTask.Error(it.localizedMessage))
                })
        )
    }

    fun getStates(): MutableLiveData<StateTask> {
        return state
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}