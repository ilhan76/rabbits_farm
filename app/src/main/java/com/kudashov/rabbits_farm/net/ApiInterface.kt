package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.response.CageServerResponse
import com.kudashov.rabbits_farm.net.response.RabbitServerResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("api/auth/token")
    fun getToken(@Body userDto: UserDto): Observable<String>

    @GET("api/rabbit/")
    fun getRabbits(@Header("Authorisation") token: String): Observable<RabbitServerResponse>

    @GET("api/cage/")
    fun getCages(@Header("Authorisation") token: String): Observable<CageServerResponse>
}