package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.ui.*

sealed class StateBirth {
    object Default : StateBirth()
    object Sending : StateBirth()
    class ListOfBirthReceived(val list: List<BirthListItemTypes>): StateBirth()
    class Error<T>(val message: T): StateBirth()
}

sealed class StateRabbit{
    object Default : StateRabbit()
    object Sending : StateRabbit()
    class SuccessRabbit(val rabbit: RabbitMoreInfUi):StateRabbit()
    class SuccessOperations(val operations: List<OperationItem>):StateRabbit()
    class Error<T>(val message: T): StateRabbit()
}

sealed class StateAboutFarm {
    object Default : StateAboutFarm()
    object Sending : StateAboutFarm()
    class ListOfRabbitsReceived(val list: List<RabbitItem>): StateAboutFarm()
    class ListOfCageReceived(val list: List<CageItem>): StateAboutFarm()
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