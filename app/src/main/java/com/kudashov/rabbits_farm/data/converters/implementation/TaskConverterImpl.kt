package com.kudashov.rabbits_farm.data.converters.implementation

import com.kudashov.rabbits_farm.data.converters.TaskConverter
import com.kudashov.rabbits_farm.data.domain.*
import com.kudashov.rabbits_farm.data.dto.TaskDto

class TaskConverterImpl: TaskConverter {
    override fun convertTaskItemFromApiToDomain(task: TaskDto, isComplete: Boolean): TaskListItemType {
        return when (task.type){
                "M" -> ReproductionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    cageTo = task.cageTo!!,
                    isDone = isComplete
                )
                "B" -> DepositionFromMotherDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    maleCageTo = task.maleCageTo!!,
                    femaleCageTo = task.femaleCageTo!!,
                    countBunnies = task.countBunnies!!,
                    isDone = isComplete
                )
                "V" -> VaccinationDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    isDone = isComplete
                )
                "I" -> InspectionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    countRabbit = task.countRabbit!!,
                    isDone = isComplete
                )
                "S" -> KillDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    weight = task.weight!!,
                    isDone = isComplete
                )
                "R" -> DepositionToReproductionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    cageTo = task.cageTo!!,
                    weight = task.weight!!,
                    isDone = isComplete
                )
                else -> DepositionToFatteningDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    cageTo = task.cageTo!!,
                    isDone = isComplete
                )
        }
    }
}