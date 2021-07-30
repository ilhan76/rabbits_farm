package com.kudashov.rabbits_farm.repository.implementation

import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import com.kudashov.rabbits_farm.repository.BirthRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class BirthRepositoryHeroku: BirthRepository {
    override fun getBirth(token: String, isConfirmed: Boolean): Observable<BirthResponse> {
        val response: PublishSubject<BirthResponse> = PublishSubject.create()

        return response
    }
}