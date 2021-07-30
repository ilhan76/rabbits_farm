package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.request.WeightRequest
import com.kudashov.rabbits_farm.net.response.*
import com.kudashov.rabbits_farm.net.response.auth.AuthResponse
import com.kudashov.rabbits_farm.net.response.farm.CageResponse
import com.kudashov.rabbits_farm.net.response.farm.OperationsResponse
import com.kudashov.rabbits_farm.net.response.farm.PostWeightResponse
import com.kudashov.rabbits_farm.net.response.farm.RabbitResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiInterface {
    @GET("api/echo/")
    fun echo(@Header("Authorization") token: String): Observable<EchoResponse>

    @POST("api/auth/token/")
    fun getToken(@Body userDto: UserDto): Observable<AuthResponse>

    @GET("api/rabbit/")
    fun getRabbits(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("farm_number") farmNumber: Int?,
        @Query("type") type: List<String>?,
        @Query("breed") breed: Int?,
        @Query("status") status: String?,
        @Query("age_from") ageFrom: Int?,
        @Query("age_to") ageTo: Int?,
        @Query("cage_number_from") cageNumberFrom: Int?,
        @Query("cage_number_to") cageNumberTo: Int?,
        @Query("is_male") isMale: Int?,
        @Query("__order_by__") orderBy: String?
    ): Observable<RabbitResponse>

    @GET("api/cage/")
    fun getCages(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("farm_number") farmNumber: Int?,
        @Query("status") status: List<String>?,
        @Query("type") type: String?,
        @Query("is_parallel") isParallel: Int?,
        @Query("number_rabbits_from") numberRabbitsFrom: Int?,
        @Query("number_rabbits_to") numberRabbitsTo: Int?,
        @Query("__order_by__") orderBy: String?
    ): Observable<CageResponse>

    @GET("api/rabbit/{id}")
    fun getRabbitMoreInf(
        @Header("Authorisation") token: String,
        @Path("id") id: Int
    ): Observable<RabbitMoreInfDto>

    @GET("api/operation/")
    fun getOperations(
        @Header("Authorisation") token: String,
        @Query("rabbit_id") id: Int
    ): Observable<OperationsResponse>

    @PUT("api/rabbit/{pathType}/{id}/")
    fun postWeight(
        @Header("Authorisation") token: String,
        @Path("pathType") pathType: String,
        @Path("id") id: Int,
        @Body weight: WeightRequest
    ) : Observable<PostWeightResponse>
}