package com.kudashov.rabbits_farm.data.source

import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.net.response.farm.*
import io.reactivex.rxjava3.core.Observable

interface FarmProvider {
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

    fun updateCageStatus(
        token: String,
        id: Int,
        statuses: List<String>
    ): Observable<BaseResponse>

    fun getBreed(token: String): Observable<BreedResponse>

    fun getRabbitMoreInf(
        token: String,
        id: Int
    ): Observable<RabbitMoreInfResponse>

    fun getOperations(
        token: String,
        id: Int
    ): Observable<OperationsResponse>

    fun isRecast(
        token: String,
        pathType: String,
        id: Int
    ): Observable<IsRecastResponse>

    fun createRecast(
        token: String,
        pathType: String,
        id: Int
    ): Observable<BaseResponse>

    fun deleteRecast(
        token: String,
        pathType: String,
        id: Int
    ): Observable<BaseResponse>

    fun postWeight(
        token: String,
        pathType: String,
        id: Int,
        weight: Double
    ): Observable<BaseResponse>
}