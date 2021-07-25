package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.response.CageServerResponse
import com.kudashov.rabbits_farm.net.response.RabbitServerResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiInterface {
    @POST("api/auth/token")
    fun getToken(@Body userDto: UserDto): Observable<String>

    @GET("api/rabbit/")
    fun getRabbits(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("farm_number") farmNumber: Int?,
        @Query("type") type: List<String>?,
        @Query("breed") breed: List<Int>?,
        @Query("status") status: List<String>?,
        @Query("age_from") ageFrom: Int?,
        @Query("age_to") ageTo: Int?,
        @Query("weight_from") weightFrom: Double?,
        @Query("weight_to") weightTo: Double?,
        @Query("is_male") isMale: Int?,
        @Query("__order_by__") orderBy: String?
    ): Observable<RabbitServerResponse>

    @GET("api/cage/")
    fun getCages(@Header("Authorisation") token: String): Observable<CageServerResponse>
}