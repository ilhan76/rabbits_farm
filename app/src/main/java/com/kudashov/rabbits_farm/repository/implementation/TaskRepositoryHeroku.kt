package com.kudashov.rabbits_farm.repository.implementation

import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import com.kudashov.rabbits_farm.repository.TaskRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class TaskRepositoryHeroku : TaskRepository {
    override fun getTasks(token: String, isDone: Boolean): Observable<TaskResponse> {
        val response: PublishSubject<TaskResponse> = PublishSubject.create()


        return response
    }
}