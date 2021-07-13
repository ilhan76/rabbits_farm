package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.Operation

data class OperationsResponse(
        override var respError: String?,
        var list: List<Operation>
): ServerResponse(respError)