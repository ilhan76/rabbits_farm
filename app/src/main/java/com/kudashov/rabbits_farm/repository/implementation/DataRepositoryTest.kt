package com.kudashov.rabbits_farm.repository.implementation

import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.item.*
import com.kudashov.rabbits_farm.net.response.*
import com.kudashov.rabbits_farm.repository.DataRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataRepositoryTest : DataRepository {

    override fun getRabbits(
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
    ): Observable<RabbitResponse> {
        val response: PublishSubject<RabbitResponse> = PublishSubject.create()

        val list: MutableList<Rabbit> = ArrayList()
        for (i in 1..20) {
            list.add(
                Rabbit(
                    i,
                    i.toString(),
                    "48 дн.",
                    false,
                    "Откорм"
                )
            )
        }

        return response
    }

    override fun getCages(): Observable<CageResponse> {
        val response: PublishSubject<CageResponse> = PublishSubject.create()

        val list: MutableList<CageDto> = ArrayList()
        for (i in 1..20) {
            list.add(
                CageDto(
                    i,
                    1,
                    i,
                    'A',
                    "M",
                    false,
                    7,
                    listOf()
                )
            )
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(
                CageResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getTasks(isDone: Boolean): Observable<TaskResponse> {
        val response: PublishSubject<TaskResponse> = PublishSubject.create()

        val list: MutableList<TasksListItemTypes> = ArrayList()
        for (i in 1..20) {
            list.add(
                Deposition(
                    "01.01.2021",
                    "113",
                    "110",
                    isDone
                )
            )
            list.add(
                Vaccination(
                    "01.01.2021",
                    "113",
                    isDone
                )
            )
            list.add(
                Inspection(
                    "01.01.2021",
                    "113",
                    null,
                    isDone
                )
            )
            list.add(
                Reproduction(
                    "01.01.2021",
                    "113",
                    "110",
                    isDone
                )
            )
            list.add(
                Kill(
                    "01.01.2021",
                    "113",
                    4.5,
                    isDone
                )
            )
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(
                TaskResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getBirth(isConfirmed: Boolean): Observable<BirthResponse> {
        val response: PublishSubject<BirthResponse> = PublishSubject.create()

        val list: MutableList<BirthListItemTypes> = ArrayList()
        for (i in 1..20) {
            if (isConfirmed)
                list.add(
                    BirthListItem(
                        "21 дн.",
                        "113Б",
                        "Оплодотворена"
                    )
                )
            else
                list.add(
                    BirthListItem(
                        "21 дн.",
                        "113Б",
                        "Не оплодотворена"
                    )
                )
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(
                BirthResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getRabbitMoreInf(id: Int): Observable<RabbitMoreInfResponse> {
        val response: PublishSubject<RabbitMoreInfResponse> = PublishSubject.create()
        return response
    }

    override fun getOperations(id: Int): Observable<OperationsResponse> {
        TODO("Not yet implemented")
    }
}