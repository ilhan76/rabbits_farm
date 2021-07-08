package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.TasksListItemTypes

data class TaskServerResponse(
        override var respError: String?,
        var tasks: List<TasksListItemTypes>
) : ServerResponse(respError)