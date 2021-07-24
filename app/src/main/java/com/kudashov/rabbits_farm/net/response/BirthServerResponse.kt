package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.item.BirthListItemTypes

class BirthServerResponse(
    val respError: String?,
    val list: List<BirthListItemTypes>
)