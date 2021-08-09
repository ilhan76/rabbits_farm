package com.kudashov.rabbits_farm.data.converters.implementation

import com.kudashov.rabbits_farm.data.converters.TaskConverter
import com.kudashov.rabbits_farm.data.domain.*
import com.kudashov.rabbits_farm.data.dto.TaskDto

class TaskConverterImpl: TaskConverter {
    override fun convertTaskItemFromApiToDomain(task: TaskDto): TaskListItemType {
        return when (task.type){
                "M" -> ReproductionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    cageTo = task.cageTo!!,
                    isDone = false
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
                    isDone = false
                )
                "V" -> VaccinationDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    isDone = false
                )
                "I" -> InspectionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    countRabbit = task.countRabbit!!,
                    isDone = false
                )
                "S" -> KillDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cage = task.cage!!,
                    weight = task.weight!!,
                    isDone = false
                )
                else -> DepositionDomain(
                    id = task.id,
                    type = task.type,
                    date = task.date.substring(0, 10),
                    userId = task.userId,
                    cageFrom = task.cageFrom!!,
                    cageTo = task.cageTo!!,
                    isDone = false
                )
            }
    }
}