package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.data.domain.CageDomain
import com.kudashov.rabbits_farm.data.domain.OperationDomain
import com.kudashov.rabbits_farm.data.domain.RabbitDomain
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfDomain
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
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
    ): Observable<Pair<Int, RepoResponse<List<RabbitDomain>>>>

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
    ): Observable<Pair<Int, RepoResponse<List<CageDomain>>>>

    fun getRabbitMoreInf(
        token: String,
        id: Int
    ): Observable<RepoResponse<RabbitMoreInfDomain>>

    fun getOperations(
        token: String,
        id: Int
    ): Observable<RepoResponse<List<OperationDomain>>>

    fun postWeight(
        token: String,
        pathType: String,
        id: Int,
        weight: Double
    ) : Observable<BaseResponse>
}