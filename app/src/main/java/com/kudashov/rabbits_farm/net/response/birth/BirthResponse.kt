package com.kudashov.rabbits_farm.net.response.birth

import com.kudashov.rabbits_farm.data.ui.BirthListItemTypes

class BirthResponse(
    val detail: String?,
    val list: List<BirthListItemTypes>
)