package com.kudashov.rabbits_farm.utilits

import com.kudashov.rabbits_farm.data.domain.*

sealed class BaseState {
    object Default : BaseState()
    object Sending : BaseState()
    class Success<T>(val content: T) : BaseState()
    class Error<E>(val error: E) : BaseState()
}

sealed class RecastState {
    object Default : RecastState()
    object Sending : RecastState()
    object Bunny : RecastState()
    object Success: RecastState()
    class IsRecast(val content: Boolean) : RecastState()
    class Error<E>(val error: E) : RecastState()
}

sealed class BaseListState {
    object Default : BaseListState()
    object Sending : BaseListState()
    object NoItem : BaseListState()
    class Success<T>(val content: T) : BaseListState()
    class Error<T>(val error: T) : BaseListState()
}

sealed class StateBirth {
    object Default : StateBirth()
    object Sending : StateBirth()
    object NoItem : StateBirth()
    class SuccessBirth(val list: List<BirthListItemTypes>) : StateBirth()
    class Error<T>(val message: T) : StateBirth()
}

/*sealed class StateRabbit {
    object Default : StateRabbit()
    object Sending : StateRabbit()
    class Success(
        val rabbit: RabbitMoreInfDomain,
        val operations: List<OperationDomain>,
        val isBaby: Boolean
    ) : StateRabbit()

    class SuccessRabbit(val rabbit: RabbitMoreInfDomain):StateRabbit()
    class SuccessOperations(val operations: List<OperationDomain>):StateRabbit()
    class Error<T>(val message: T) : StateRabbit()
}*/

sealed class StateFarm {
    object Default : StateFarm()
    object Sending : StateFarm()
    object NoItem : StateFarm()
    class SuccessRabbits(val list: List<RabbitDomain>) : StateFarm()
    class SuccessCage(val list: List<CageDomain>) : StateFarm()
    class Error<T>(val message: T) : StateFarm()
}

sealed class StateTask {
    object Default : StateTask()
    object Sending : StateTask()
    object NoItem : StateTask()
    object NotAllFieldsAreFilledIn : StateTask()
    object DeathNotMatherCage: StateTask()
    object DeathNotBunnies: StateTask()
    object DeathNotRabbit: StateTask()
    object DeathCageNotExist: StateTask()
    class ListOfTaskReceived(val list: List<TaskListItemType>) : StateTask()
    class Error<T>(val message: T) : StateTask()
}

sealed class StateAuth {
    object Default : StateAuth()
    object Sending : StateAuth()
    object OutdatedToken : StateAuth()
    object ActualToken: StateAuth()
    class Success(val message: String) : StateAuth()
    class Error<T>(val message: T) : StateAuth()
}