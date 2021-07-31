package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.PutResponse
import com.kudashov.rabbits_farm.net.response.farm.*
import io.reactivex.rxjava3.core.Observable

interface FarmRepository {
    fun getRabbits(
        token: String,
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

    fun getCages(
        token: String,
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        status: List<String>?,
        type: String?,
        isParallel: Int?,
        numberRabbitsFrom: Int?,
        numberRabbitsTo: Int?,
        orderBy: String?
    ): Observable<CageResponse>

    fun getRabbitMoreInf(token: String, id: Int): Observable<RabbitMoreInfResponse>

    fun getOperations(token: String, id: Int): Observable<OperationsResponse>

    fun postWeight(token: String, pathType: String, id: Int, weight: Double) : Observable<PutResponse>
}