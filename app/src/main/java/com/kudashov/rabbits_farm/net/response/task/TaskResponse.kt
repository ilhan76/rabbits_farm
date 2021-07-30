package com.kudashov.rabbits_farm.net.response.task

import com.kudashov.rabbits_farm.data.item.TasksListItemTypes

data class TaskResponse(
    val detail: String?,
    val tasks: List<TasksListItemTypes>
)