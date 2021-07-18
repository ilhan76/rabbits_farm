package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.*

sealed class StateBirth {
    class Default: StateBirth()
    class Sending: StateBirth()
    class ListOfBirthReceived(val list: List<BirthListItemTypes>): StateBirth()
    class Error<T>(val message: T): StateBirth()
}

sealed class StateRabbit{
    class Default(): StateRabbit()
    class Sending():StateRabbit()
    class Success(var list: List<Operation>):StateRabbit()
    class Error<T>(var message: T): StateRabbit()
}

sealed class StateAboutFarm {
    class Default: StateAboutFarm()
    class Sending: StateAboutFarm()
    class ListOfRabbitsReceived(val list: List<Rabbit>): StateAboutFarm()
    class ListOfCageReceived(val list: List<Cage>): StateAboutFarm()
    class Error<T>(val message: T): StateAboutFarm()
}

sealed class StateAboutFarmMenu {
    class Default: StateAboutFarmMenu()
    class Sending: StateAboutFarmMenu()
    class ListReceivedSuccessfully<T>(val list: List<T>): StateAboutFarmMenu()
    class Error<T>(val message: T): StateAboutFarmMenu()
}

sealed class StateTasks {
    class Default: StateTasks()
    class Sending: StateTasks()
    class ListOfTasksReceived(val list: List<TasksListItemTypes>): StateTasks()
    class Error<T>(val message: T): StateTasks()
}