package com.kudashov.rabbits_farm.repository

import com.kudashov.rabbits_farm.net.response.AuthResponse
import io.reactivex.rxjava3.core.Observable

interface AuthRepository {
    fun auth(username: String, pass: String): Observable<AuthResponse>
}