package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.dto.UserDto
import com.kudashov.rabbits_farm.net.request.birth.ConfirmPregnancyRequest
import com.kudashov.rabbits_farm.net.request.birth.TakeBirthRequest
import com.kudashov.rabbits_farm.net.request.farm.WeightRequest
import com.kudashov.rabbits_farm.net.request.task.BunnyJiggingTaskRequest
import com.kudashov.rabbits_farm.net.request.task.DeathRequest
import com.kudashov.rabbits_farm.net.request.task.SlaughterInspectionTaskRequest
import com.kudashov.rabbits_farm.net.response.*
import com.kudashov.rabbits_farm.net.response.auth.AuthResponse
import com.kudashov.rabbits_farm.net.response.birth.BirthResponse
import com.kudashov.rabbits_farm.net.response.farm.CageResponse
import com.kudashov.rabbits_farm.net.response.farm.OperationsResponse
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.farm.RabbitResponse
import com.kudashov.rabbits_farm.net.response.task.TaskResponse
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
    ): Observable<BaseResponse>

    @GET("api/task/in_progress/")
    fun getTasks(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("__order_by__") orderBy: String?
    ): Observable<TaskResponse>

    @PUT("api/task/in_progress/{id}/")
    fun confirmSimpleTask(
        @Header("Authorisation") token: String,
        @Path("id") id: Int
    ): Observable<BaseResponse>

    @PUT("api/task/in_progress/bunny_jigging/{id}/")
    fun confirmBunnyJigging(
        @Header("Authorisation") token: String,
        @Path("id") id: Int,
        @Body body: BunnyJiggingTaskRequest
    ) : Observable<BaseResponse>

    @PUT("api/task/in_progress/slaughter_inspection/{id}/")
    fun confirmSlaughterInspection(
        @Header("Authorisation") token: String,
        @Path("id") id: Int,
        @Body body: SlaughterInspectionTaskRequest
    ): Observable<BaseResponse>

    @GET("/api/birth/confirmed/")
    fun getBirthConfirmed(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("__order_by__") orderBy: String?
    ): Observable<BirthResponse>

    @GET("/api/birth/unconfirmed/")
    fun getBirthUnconfirmed(
        @Header("Authorisation") token: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("__order_by__") orderBy: String?
    ): Observable<BirthResponse>

    @PUT("/api/birth/unconfirmed/{id}/")
    fun confirmPregnancy(
        @Header("Authorisation") token: String,
        @Path("id") id: Int,
        @Body body: ConfirmPregnancyRequest
    ): Observable<BaseResponse>

    @PUT("/api/birth/confirmed/{id}/")
    fun takeBirth(
        @Header("Authorisation") token: String,
        @Path("id") id: Int,
        @Body body: TakeBirthRequest
    ): Observable<BaseResponse>

    @POST("api/rabbit/death/")
    fun death(
        @Header("Authorisation") token: String,
        @Body body: DeathRequest
    ) : Observable<BaseResponse>
}