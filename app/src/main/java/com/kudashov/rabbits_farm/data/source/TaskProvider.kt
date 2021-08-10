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

    fun confirmSimpleTask(
        token: String,
        id: Int
    ): Observable<BaseResponse>

    fun confirmSlaughterInspectionTask(
        token: String,
        id: Int,
        weights: List<Double>
    ): Observable<BaseResponse>

    fun confirmDepositionFromMotherTask(
        token: String,
        id: Int,
        countMales: Int
    ): Observable<BaseResponse>

    fun putDeath(
        token: String,
        farmNumber: Int?,
        cageNumber: Int?,
        letter: String?,
        deathCause: String?
    ): Observable<BaseResponse>
}