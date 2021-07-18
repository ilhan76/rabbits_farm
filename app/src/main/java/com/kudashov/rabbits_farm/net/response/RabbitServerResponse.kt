package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.RabbitDto

data class RabbitServerResponse(

    override var respError: String?,
    var rabbits: List<RabbitDto>?

): ServerResponse(respError)