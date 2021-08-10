package com.kudashov.rabbits_farm.net.request.task

import com.google.gson.annotations.SerializedName

class SlaughterInspectionTaskRequest(
    @SerializedName("completed_at")
    val completedAt: String? = null,
    @SerializedName("weights")
    val weights: List<Double>?
)