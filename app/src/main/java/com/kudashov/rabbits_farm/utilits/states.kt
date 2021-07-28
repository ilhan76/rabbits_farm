package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.item.BirthListItemTypes
import com.kudashov.rabbits_farm.data.item.Cage
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.data.item.TasksListItemTypes

sealed class StateBirth {
    class Default: StateBirth()
    class Sending: StateBirth()
    class ListOfBirthReceived(val list: List<BirthListItemTypes>): StateBirth()
    class Error<T>(val message: T): StateBirth()
}

sealed class StateRabbit{
    class Default(): StateRabbit()
    class Sending():StateRabbit()
    class Success(var list: List<OperationDto>):StateRabbit()
    class Error<T>(var message: T): StateRabbit()
}

sealed class StateAboutFarm {
    class Default: StateAboutFarm()
    class Sending: StateAboutFarm()
    class ListOfRabbitsReceived(val list: List<Rabbit>): StateAboutFarm()
    class ListOfCageReceived(val list: List<Cage>): StateAboutFarm()
    class Error<T>(val message: T): StateAboutFarm()
}

sealed class StateTasks {
    class Default: StateTasks()
    class Sending: StateTasks()
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