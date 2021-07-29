package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path

interface DataRepository {

    fun getRabbits(
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        type: List<String>?,
        breed: Int?,
        status: String?,
        ageFrom: Int?,
        ageTo: Int?,
        cageNumberFrom: Int?,
        cageNumberTo: Int?,
        isMale: Int?,
        orderBy: String?
    ): Observable<RabbitResponse>

    fun getCages(): Observable<CageResponse>

    fun getTasks(isDone: Boolean): Observable<TaskResponse>

    fun getBirth(isConfirmed: Boolean): Observable<BirthResponse>

    fun getRabbitMoreInf(id: Int): Observable<RabbitMoreInfResponse>

    fun getOperations(id: Int): Observable<OperationsResponse>

    fun postWeight(token: String, type: String, id: Int, weight: Double)
}