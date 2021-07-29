package com.kudashov.rabbits_farm.screens.tasks

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.repository.TaskRepository
import com.kudashov.rabbits_farm.repository.implementation.TaskRepositoryHeroku
import com.kudashov.rabbits_farm.utilits.StateTasks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TasksViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateTasks> = MutableLiveData()
    private val repository: TaskRepository = TaskRepositoryHeroku()

    fun getTasks(isDone: Boolean){
        state.postValue(StateTasks.Sending)

        repository.getTasks(isDone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.detail?.isEmpty()!!){
                        Log.i(TAG, "getRabbits: SUCCESS")
                        state.postValue(StateTasks.ListOfTasksReceived(response.tasks))
                    } else {
                        Log.i(TAG, "getRabbits: ERROR")
                        state.postValue(StateTasks.Error("Error"))
                    }
                }
    }


    fun getStates(): MutableLiveData<StateTasks> {
        return state
    }

}