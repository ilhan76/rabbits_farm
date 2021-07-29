package com.kudashov.rabbits_farm.repository.implementation

import android.content.Context
import android.content.SharedPreferences
import com.kudashov.rabbits_farm.net.response.TaskResponse
import com.kudashov.rabbits_farm.repository.TaskRepository
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.const.APP_PREFERENCE
import com.kudashov.rabbits_farm.utilits.const.USER_TOKEN
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class TaskRepositoryHeroku : TaskRepository {
    override fun getTasks(isDone: Boolean): Observable<TaskResponse> {
        val response: PublishSubject<TaskResponse> = PublishSubject.create()

        val pref: SharedPreferences = APP_ACTIVITY.getSharedPreferences(
            APP_PREFERENCE, Context.MODE_PRIVATE)
        val token = "Token ${pref.getString(USER_TOKEN, "")}"


        return response
    }
}