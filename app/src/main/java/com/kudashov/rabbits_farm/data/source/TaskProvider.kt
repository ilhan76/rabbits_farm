package com.kudashov.rabbits_farm.data.source

import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import io.reactivex.rxjava3.core.Observable

interface TaskProvider {
    fun getTasks(
        token: String,
        isDone: Boolean,
        page: Int,
        pageSize: Int,
        orderBy: String?
    ): Observable<TaskResponse>

    fun putDeath(
        token: String,
        farmNumber: Int?,
        cageNumber: Int?,
        letter: String?,
        deathCause: String?
    ): Observable<BaseResponse>
}