package com.kudashov.rabbits_farm.screens.tasks

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kudashov.rabbits_farm.data.TasksListItemTypes
import com.kudashov.rabbits_farm.repository.DataRepository
import com.kudashov.rabbits_farm.repository.implementation.DataRepositoryTest
import com.kudashov.rabbits_farm.screens.aboutFarm.StateAboutFarm
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class StateTasks {
    class Default: StateTasks()
    class Sending: StateTasks()
    class ListOfTasksReceived(val list: List<TasksListItemTypes>): StateTasks()
    class Error<T>(val message: T): StateTasks()
}
class TasksViewModel(application: Application): AndroidViewModel(application) {

    private val TAG: String = this::class.java.simpleName
    private val state: MutableLiveData<StateTasks> = MutableLiveData()
    private val repository: DataRepository = DataRepositoryTest()

    fun getTasks(){
        state.postValue(StateTasks.Sending())

        repository.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.respError?.isEmpty()!!){
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