package com.kudashov.rabbits_farm.repository.implementation

import com.kudashov.rabbits_farm.data.*
import com.kudashov.rabbits_farm.net.CageServerResponse
import com.kudashov.rabbits_farm.net.RabbitServerResponse
import com.kudashov.rabbits_farm.net.TaskServerResponse
import com.kudashov.rabbits_farm.repository.DataRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataRepositoryTest: DataRepository {

    override fun getRabbits(): Observable<RabbitServerResponse> {
        val response: PublishSubject<RabbitServerResponse> = PublishSubject.create()

        val list: MutableList<Rabbit> = ArrayList()
        for (i in 1..20) {
            list.add(Rabbit(i.toString(), "48 дн.", "Откорм"))
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(RabbitServerResponse("", list))
        }

        return response
    }

    override fun getCages(): Observable<CageServerResponse> {
        val response: PublishSubject<CageServerResponse> = PublishSubject.create()

        val list: MutableList<Cage> = ArrayList()
        for (i in 1..20) {
            list.add(Cage(i.toString(), "1", "МАТ 1", "Уб., Рем."))
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(CageServerResponse("", list))
        }

        return response
    }

    override fun getTasks(): Observable<TaskServerResponse> {
        val response: PublishSubject<TaskServerResponse> = PublishSubject.create()

        val list: MutableList<TasksListItemTypes> = ArrayList()
        for (i in 1..20) {
            list.add(Deposition("01.01.2021", "113", "110", false))
            list.add(Vaccination("01.01.2021", "113",  false))
            list.add(Inspection("01.01.2021", "113",  null, false))
            list.add(Reproduction("01.01.2021", "113", "110", false))
            list.add(Kill("01.01.2021", "113", 4.5, false))
        }

        GlobalScope.launch() {
            delay(100)
            response.onNext(TaskServerResponse("", list))
        }

        return response
    }
}