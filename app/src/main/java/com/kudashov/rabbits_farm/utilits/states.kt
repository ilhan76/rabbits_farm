package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.domain.*

sealed class StateBirth {
    object Default : StateBirth()
    object Sending : StateBirth()
    object NoItem: StateBirth()
    class SuccessBirth(val list: List<BirthListItemTypes>): StateBirth()
    class Error<T>(val message: T): StateBirth()
}

sealed class StateRabbit{
    object Default : StateRabbit()
    object Sending : StateRabbit()
    class SuccessRabbit(val rabbit: RabbitMoreInfDomain):StateRabbit()
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

sealed class StateTask {
    object Default : StateTask()
    object Sending : StateTask()
    object NoItem: StateTask()
    object NotAllFieldsAreFilledIn: StateTask()
    class ListOfTaskReceived(val list: List<TaskListItemType>): StateTask()
    class Error<T>(val message: T): StateTask()
}

sealed class StateAuth {
    object Default : StateAuth()
    object Sending : StateAuth()
    class Success(val message: String): StateAuth()
    object OutdatedToken : StateAuth()
    class Error<T>(val message: T): StateAuth()
}