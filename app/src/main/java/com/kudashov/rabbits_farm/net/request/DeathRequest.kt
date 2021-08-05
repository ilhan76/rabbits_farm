package com.kudashov.rabbits_farm.net.request

import com.kudashov.rabbits_farm.data.dto.CageSimpleDto

class DeathRequest (
    val cage: CageSimpleDto,
    val death_cause: String?
)