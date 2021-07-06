package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.CageServerResponse
import com.kudashov.rabbits_farm.net.RabbitServerResponse
import io.reactivex.rxjava3.core.Observable

interface DataRepository {
    fun getRabbits(): Observable<RabbitServerResponse>
    fun getCages(): Observable<CageServerResponse>
}