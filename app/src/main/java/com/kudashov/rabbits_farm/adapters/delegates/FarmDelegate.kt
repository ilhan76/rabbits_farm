package com.kudashov.rabbits_farm.adapters.delegates

import com.kudashov.rabbits_farm.data.ui.RabbitItem

interface FarmDelegate {
    fun openMoreRabbitInfo(rabbit: RabbitItem)
}