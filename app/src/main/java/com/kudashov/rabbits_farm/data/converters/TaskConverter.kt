package com.kudashov.rabbits_farm.data.converters

import com.kudashov.rabbits_farm.data.domain.TaskListItemType
import com.kudashov.rabbits_farm.data.dto.TaskDto

interface TaskConverter {
    fun convertTaskItemFromApiToDomain(task: TaskDto): TaskListItemType
}