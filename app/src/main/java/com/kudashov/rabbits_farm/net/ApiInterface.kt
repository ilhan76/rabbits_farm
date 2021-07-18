package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.dto.UserDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("api/auth/token")
    fun getToken(@Body userDto: UserDto): Observable<String>

    @GET("api/rabbit/")
    fun getRabbits(): Observable<List<RabbitDto>>
}