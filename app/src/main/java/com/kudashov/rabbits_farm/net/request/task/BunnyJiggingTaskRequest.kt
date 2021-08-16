package com.kudashov.rabbits_farm.net.request.task

import com.google.gson.annotations.SerializedName

class BunnyJiggingTaskRequest(
    @SerializedName("completed_at")
    val completedAt: String? = null,
    @SerializedName("males")
    val males: Int?
)