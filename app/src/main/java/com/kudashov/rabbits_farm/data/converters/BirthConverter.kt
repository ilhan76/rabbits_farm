package com.kudashov.rabbits_farm.data.converters

import com.kudashov.rabbits_farm.data.domain.BirthDomain
import com.kudashov.rabbits_farm.data.dto.BirthDto

interface BirthConverter {
    fun convertBirthFromApiToDomain(birthDto: BirthDto): BirthDomain
}