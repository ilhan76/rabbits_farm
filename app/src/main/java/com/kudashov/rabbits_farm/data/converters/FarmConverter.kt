package com.kudashov.rabbits_farm.data.converters

import com.kudashov.rabbits_farm.data.domain.CageDomain
import com.kudashov.rabbits_farm.data.domain.OperationDomain
import com.kudashov.rabbits_farm.data.domain.RabbitDomain
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfDomain
import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto

interface FarmConverter {
    fun convertRabbitFromApiToDomain(rabbitDto: RabbitDto): RabbitDomain

    fun convertRabbitMoreInfFromApiToDomain(rabbit: RabbitMoreInfDto): RabbitMoreInfDomain

    fun convertCageFromApiToDomain(cageDto: CageDto): CageDomain

    fun convertOperationFromApiToDomain(operationDto: OperationDto): OperationDomain
}