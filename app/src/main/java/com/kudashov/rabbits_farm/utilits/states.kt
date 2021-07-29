package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.item.BirthListItemTypes
import com.kudashov.rabbits_farm.data.item.Cage
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.data.item.TasksListItemTypes

sealed class StateBirth {
    object Default : StateBirth()
    object Sending : StateBirth()
    class ListOfBirthReceived(val list: List<BirthListItemTypes>): StateBirth()
    class Error<T>(val message: T): StateBirth()
}

sealed class StateRabbit{
    object Default : StateRabbit()
    object Sending : StateRabbit()
    class SuccessRabbit(var rabbit: RabbitMoreInfDto):StateRabbit()
    class SuccessOperations(var operations: List<OperationDto>):StateRabbit()
    class Error<T>(var message: T): StateRabbit()
}

sealed class StateAboutFarm {
    object Default : StateAboutFarm()
    object Sending : StateAboutFarm()
    class ListOfRabbitsReceived(val list: List<Rabbit>): StateAboutFarm()
    class ListOfCageReceived(val list: List<Cage>): StateAboutFarm()
    class Error<T>(val message: T): StateAboutFarm()
}

sealed class StateTasks {
    object Default : StateTasks()
    object Sending : StateTasks()
    class ListOfTasksReceived(val list: List<TasksListItemTypes>): StateTasks()
    class Error<T>(val message: T): StateTasks()
}

sealed class StateAuth {
    object Default : StateAuth()
    object Sending : StateAuth()
    class Success(val message: String): StateAuth()
    object OutdatedToken : StateAuth()
    class Error<T>(val message: T): StateAuth()
}