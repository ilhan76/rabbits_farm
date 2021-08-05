package com.kudashov.rabbits_farm.screens.tasks

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.extensions.default
import com.kudashov.rabbits_farm.repository.TaskRepository
import com.kudashov.rabbits_farm.repository.implementation.TaskRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateTasks
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class TasksViewModel(val context: Application) : AndroidViewModel(context), Serializable {

    private val TAG: String = this::class.java.simpleName
    private val state = MutableLiveData<StateTasks>().default(initialValue = StateTasks.Default)
    private val repository: TaskRepository = TaskRepositoryHeroku()
    private val compositeDisposable = CompositeDisposable()

    private var deathCause: String? = null

    fun setDeathCause(cause: String?) {
        deathCause = cause
    }

    fun getTasks(isDone: Boolean) {
        state.postValue(StateTasks.Sending)

        val pref = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        compositeDisposable.add(repository.getTasks(token, isDone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response.detail?.isEmpty()!!) {
                    Log.i(TAG, "getRabbits: SUCCESS")
                    state.postValue(StateTasks.ListOfTasksReceived(response.tasks))
                } else {
                    Log.i(TAG, "getRabbits: ERROR")
                    state.postValue(StateTasks.Error("Error"))
                }
            })
    }

    fun putDeath(farmNumber: Int, cageNumber: Int, letter: String) {
        state.postValue(StateTasks.Sending)

        val pref = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"

        compositeDisposable.add(
            repository.putDeath(token, farmNumber, cageNumber, letter, deathCause)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.d(TAG, "putDeath: Success")
                    state.postValue(StateTasks.Default)
                }, {
                    Log.d(TAG, "putDeath: Error")
                    state.postValue(StateTasks.Error(it.localizedMessage))
                })
        )
    }

    fun getStates(): MutableLiveData<StateTasks> {
        return state
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}