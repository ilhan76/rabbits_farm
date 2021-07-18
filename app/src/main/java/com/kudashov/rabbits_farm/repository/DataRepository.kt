package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.*
import io.reactivex.rxjava3.core.Observable

interface DataRepository {

    fun getRabbits(): Observable<RabbitServerResponse>

    fun getCages(): Observable<CageServerResponse>

    fun getTasks(isDone: Boolean): Observable<TaskServerResponse>

    fun getBirth(isConfirmed: Boolean): Observable<BirthServerResponse>

    fun getOperations(): Observable<OperationsResponse>
}