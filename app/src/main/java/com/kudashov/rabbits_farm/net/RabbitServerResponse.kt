package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.data.Rabbit

data class RabbitServerResponse(override var respError: String?, var rabbits: List<Rabbit>?): ServerResponse(respError)