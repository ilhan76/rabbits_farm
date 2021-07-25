package com.kudashov.rabbits_farm.repository.implementation

import com.kudashov.rabbits_farm.data.*
import com.kudashov.rabbits_farm.data.dto.CageDto
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
        breed: List<Int>?,
        status: List<String>?,
        ageFrom: Int?,
        ageTo: Int?,
        weightFrom: Double?,
        weightTo: Double?,
        isMale: Int?,
        orderBy: String?
    ): Observable<RabbitServerResponse> {
        val response: PublishSubject<RabbitServerResponse> = PublishSubject.create()

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

    override fun getCages(): Observable<CageServerResponse> {
        val response: PublishSubject<CageServerResponse> = PublishSubject.create()

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
                CageServerResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getTasks(isDone: Boolean): Observable<TaskServerResponse> {
        val response: PublishSubject<TaskServerResponse> = PublishSubject.create()

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
                TaskServerResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getBirth(isConfirmed: Boolean): Observable<BirthServerResponse> {
        val response: PublishSubject<BirthServerResponse> = PublishSubject.create()

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
                BirthServerResponse(
                    "",
                    list
                )
            )
        }

        return response
    }

    override fun getOperations(): Observable<OperationsResponse> {
        val response: PublishSubject<OperationsResponse> = PublishSubject.create()

        val list: MutableList<Operation> = ArrayList()
        for (i in 1..20) {
            list.add(Operation("01.01.2021", "Какой-то статус"))
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(
                OperationsResponse(
                    "",
                    list
                )
            )
        }

        return response
    }
}