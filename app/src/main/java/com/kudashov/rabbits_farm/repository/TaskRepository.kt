package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.data.domain.TaskListItemType
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.net.response.task.DeathResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getTasks(
        token: String,
        isDone: Boolean,
        page: Int,
        pageSize: Int,
        userId: Int,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<TaskListItemType>>>>

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
    ): Observable<DeathResponse>
}