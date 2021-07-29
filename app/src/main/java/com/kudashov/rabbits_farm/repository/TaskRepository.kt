package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.TaskResponse
import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getTasks(isDone: Boolean): Observable<TaskResponse>
}