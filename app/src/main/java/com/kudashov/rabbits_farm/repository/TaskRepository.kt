package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getTasks(token: String, isDone: Boolean): Observable<TaskResponse>
}