package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import io.reactivex.rxjava3.core.Observable

interface BirthRepository {
    fun getBirth(
        token: String,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<BirthResponse>
}