package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.item.BirthListItemTypes

class BirthResponse(
    val detail: String?,
    val list: List<BirthListItemTypes>
)