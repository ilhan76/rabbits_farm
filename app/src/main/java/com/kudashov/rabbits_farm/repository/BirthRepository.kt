package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.data.domain.BirthDomain
import com.kudashov.rabbits_farm.net.request.birth.ConfirmPregnancyRequest
import com.kudashov.rabbits_farm.net.request.birth.TakeBirthRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import io.reactivex.rxjava3.core.Observable

interface BirthRepository {
    fun getBirth(
        token: String,
        page: Int,
        pageSize: Int,
        isConfirmed: Boolean,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<BirthDomain>>>>

    fun confirmPregnancy(
        token: String,
        id: Int,
        confirm: ConfirmPregnancyRequest
    ): Observable<BaseResponse>


    fun takeBirth(
        token: String,
        id: Int,
        count: TakeBirthRequest
    ): Observable<BaseResponse>
}