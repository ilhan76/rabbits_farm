package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.TasksListItemTypes
import com.kudashov.rabbits_farm.net.response.ServerResponse

data class TaskServerResponse(
        override var respError: String?,
        var tasks: List<TasksListItemTypes>
) : ServerResponse(respError)