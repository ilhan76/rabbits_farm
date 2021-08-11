package com.kudashov.rabbits_farm.net.response.task

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.data.dto.TaskDto

data class TaskResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("results")
    val tasks: List<TaskDto>?
)