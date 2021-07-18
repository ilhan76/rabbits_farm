package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.BirthListItemTypes

class BirthServerResponse(
        override val respError: String?,
        val list: List<BirthListItemTypes>
): ServerResponse(respError)