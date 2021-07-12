package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.*
import io.reactivex.rxjava3.core.Observable

interface DataRepository {
    fun getRabbits(): Observable<RabbitServerResponse>
    fun getCages(): Observable<CageServerResponse>

    fun getTasks(): Observable<TaskServerResponse>

    fun getBirth(): Observable<BirthServerResponse>

    fun getOperations(): Observable<OperationsResponse>
}