package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.*
import io.reactivex.rxjava3.core.Observable

interface DataRepository {

    fun getRabbits(
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        type: List<String>?,
        breed: List<Int>?,
        status: List<String>?,
        ageFrom: Int?,
        ageTo: Int?,
        weightFrom: Double?,
        weightTo: Double?,
        isMale: Int?,
        orderBy: String?
    ): Observable<RabbitServerResponse>

    fun getCages(): Observable<CageServerResponse>

    fun getTasks(isDone: Boolean): Observable<TaskServerResponse>

    fun getBirth(isConfirmed: Boolean): Observable<BirthServerResponse>

    fun getOperations(): Observable<OperationsResponse>
}