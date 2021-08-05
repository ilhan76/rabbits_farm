package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.request.ConfirmRequest
import com.kudashov.rabbits_farm.net.request.TakeBirthRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import io.reactivex.rxjava3.core.Observable

interface BirthRepository {
    fun getBirth(
        token: String,
        page: Int,
        pageSize: Int,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<BirthResponse>

    fun confirmPregnancy(
        token: String,
        id: Int,
        confirm: ConfirmRequest
    ): Observable<BaseResponse>


    fun takeBirth(
        token: String,
        id: Int,
        count: TakeBirthRequest
    ): Observable<BaseResponse>
}