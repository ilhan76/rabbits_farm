package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.Operation

data class OperationsResponse(
    val respError: String?,
    val list: List<Operation>
)