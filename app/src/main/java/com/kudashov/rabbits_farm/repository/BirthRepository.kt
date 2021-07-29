package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.BirthResponse
import io.reactivex.rxjava3.core.Observable

interface BirthRepository {
    fun getBirth(isConfirmed: Boolean): Observable<BirthResponse>
}