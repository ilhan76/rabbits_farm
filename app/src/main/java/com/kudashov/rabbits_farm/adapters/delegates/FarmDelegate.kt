package com.kudashov.rabbits_farm.adapters.delegates

import com.kudashov.rabbits_farm.data.Rabbit

interface FarmDelegate {
    fun openMoreRabbitInfo(rabbit: Rabbit)
}