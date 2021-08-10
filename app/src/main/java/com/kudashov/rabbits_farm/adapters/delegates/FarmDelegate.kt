package com.kudashov.rabbits_farm.adapters.delegates

import com.kudashov.rabbits_farm.data.domain.RabbitDomain

interface FarmDelegate {
    fun openMoreRabbitInfo(rabbit: RabbitDomain)
}