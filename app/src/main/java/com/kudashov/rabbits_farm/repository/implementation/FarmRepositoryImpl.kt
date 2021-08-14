package com.kudashov.rabbits_farm.repository.implementation

import android.util.Log
import com.kudashov.rabbits_farm.data.converters.FarmConverter
import com.kudashov.rabbits_farm.data.domain.*
import com.kudashov.rabbits_farm.data.source.FarmProvider
import com.kudashov.rabbits_farm.net.ApiClient
import com.kudashov.rabbits_farm.net.ApiInterface
import com.kudashov.rabbits_farm.net.request.farm.WeightRequest
import com.kudashov.rabbits_farm.net.response.BaseResponse
import com.kudashov.rabbits_farm.net.response.RepoResponse
import com.kudashov.rabbits_farm.repository.FarmRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class FarmRepositoryImpl(
    val converter: FarmConverter,
    val provider: FarmProvider
) : FarmRepository {
    private val TAG: String = this::class.java.simpleName

    override fun getRabbits(
        token: String,
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        type: List<String>?,
        breed: Int?,
        status: String?,
        ageFrom: Int?,
        ageTo: Int?,
        cageNumberFrom: Int?,
        cageNumberTo: Int?,
        isMale: Int?,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<RabbitDomain>>>> {
        val resp: PublishSubject<Pair<Int, RepoResponse<List<RabbitDomain>>>> =
            PublishSubject.create()

        provider.getRabbits(
            token,
            page,
            pageSize,
            farmNumber,
            type,
            breed,
            status,
            ageFrom,
            ageTo,
            cageNumberFrom,
            cageNumberTo,
            isMale,
            orderBy
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getRabbits: Success")
                resp.onNext(
                    Pair(
                        it.count, RepoResponse(
                            it.results?.map { rabbitDto ->
                                converter.convertRabbitFromApiToDomain(rabbitDto)
                            }, it.detail
                        )
                    )
                )
            }, {
                Log.d(TAG, "getRabbits: Error")
                resp.onNext(Pair(0, RepoResponse<List<RabbitDomain>>(null, it.localizedMessage)))
            })

        return resp
    }

    override fun getCages(
        token: String,
        page: Int,
        pageSize: Int,
        farmNumber: Int?,
        status: List<String>?,
        type: String?,
        isParallel: Int?,
        numberRabbitsFrom: Int?,
        numberRabbitsTo: Int?,
        orderBy: String?
    ): Observable<Pair<Int, RepoResponse<List<CageDomain>>>> {
        val resp: PublishSubject<Pair<Int, RepoResponse<List<CageDomain>>>> =
            PublishSubject.create()

        provider.getCages(
            token,
            page,
            pageSize,
            farmNumber,
            status,
            type,
            isParallel,
            numberRabbitsFrom,
            numberRabbitsTo,
            orderBy
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getCages: Success")
                resp.onNext(
                    Pair(
                        it.count, RepoResponse(
                            it.results?.map { cageDto ->
                                converter.convertCageFromApiToDomain(cageDto)
                            }, it.detail
                        )
                    )
                )
            }, {
                Log.d(TAG, "getCages: Error")
                resp.onNext(Pair(0, RepoResponse<List<CageDomain>>(null, it.localizedMessage)))
            })

        return resp
    }

    override fun getBreed(token: String): Observable<RepoResponse<List<BreedDomain>>> {
        val resp: PublishSubject<RepoResponse<List<BreedDomain>>> =
            PublishSubject.create()

        provider.getBreed(
            token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getCages: Success")
                resp.onNext(
                    RepoResponse(
                            it.breeds?.map { breedDto ->
                                converter.convertBreedFromApiToDomain(breedDto)
                            }, it.detail
                        )
                )
            }, {
                Log.d(TAG, "getCages: Error")
                resp.onNext(RepoResponse(null, it.localizedMessage))
            })

        return resp
    }

    override fun getRabbitMoreInf(
        token: String,
        id: Int
    ): Observable<RepoResponse<RabbitMoreInfDomain>> {
        val resp: PublishSubject<RepoResponse<RabbitMoreInfDomain>> = PublishSubject.create()

        provider.getRabbitMoreInf(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getRabbitMoreInf: Success")
                resp.onNext(
                    RepoResponse(
                        converter.convertRabbitMoreInfFromApiToDomain(it.rabbit!!), it.detail
                    )
                )
            }, {
                Log.d(TAG, "getRabbitMoreInf: Error")
                resp.onNext(RepoResponse(null, it.localizedMessage))
            })

        return resp
    }

    override fun getOperations(
        token: String,
        id: Int
    ): Observable<RepoResponse<List<OperationDomain>>> {
        val resp: PublishSubject<RepoResponse<List<OperationDomain>>> = PublishSubject.create()

        provider.getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getOperations: Success")
                resp.onNext(
                    RepoResponse(
                        it.results?.map { operationDto ->
                            converter.convertOperationFromApiToDomain(operationDto)
                        }, it.detail
                    )
                )
            }, {
                Log.d(TAG, "getOperations: Error")
                resp.onNext(RepoResponse(null, it.localizedMessage))
            })

        return resp
    }

    override fun isRecast(
        token: String,
        pathType: String,
        id: Int
    ): Observable<RepoResponse<Boolean>> {
        val resp: PublishSubject<RepoResponse<Boolean>> = PublishSubject.create()

        provider.isRecast(token, pathType, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "isRecast: Success")
                resp.onNext(RepoResponse(it.waitingRecast, null))
            }, {
                resp.onError(it)
            })

        return resp
    }

    override fun createRecast(token: String, pathType: String, id: Int): Observable<BaseResponse> {
        val resp: PublishSubject<BaseResponse> = PublishSubject.create()

        provider.createRecast(token, pathType, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "isRecast: Success")
                resp.onNext(BaseResponse(null))
            }, {
                resp.onError(it)
            })

        return resp
    }

    override fun deleteRecast(token: String, pathType: String, id: Int): Observable<BaseResponse> {
        val resp: PublishSubject<BaseResponse> = PublishSubject.create()

        provider.deleteRecast(token, pathType, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "isRecast: Success")
                resp.onNext(BaseResponse(null))
            }, {
                resp.onError(it)
            })

        return resp
    }

    override fun postWeight(
        token: String,
        pathType: String,
        id: Int,
        weight: Double
    ): Observable<BaseResponse> {
        val resp: PublishSubject<BaseResponse> = PublishSubject.create()

        provider.getOperations(token, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "getOperations: Success")
                resp.onNext(BaseResponse(null))

            }, {
                Log.d(TAG, "getOperations: Error")
                resp.onNext(
                    BaseResponse(
                        it?.localizedMessage
                    )
                )
            })
        ApiClient.client.create(ApiInterface::class.java)
            .postWeight(token, pathType, id, WeightRequest(weight = weight))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "onNext: The data was sent successfully")
                resp.onNext(BaseResponse(null))
            }, {
                Log.d(TAG, "onError: ${it?.localizedMessage}")
                resp.onNext(BaseResponse(it?.localizedMessage))
            })
        return resp
    }
}