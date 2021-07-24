package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.item.TasksListItemTypes

data class TaskServerResponse(
    val respError: String?,
    val tasks: List<TasksListItemTypes>
)