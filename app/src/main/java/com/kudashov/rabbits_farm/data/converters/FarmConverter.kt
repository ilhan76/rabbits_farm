package com.kudashov.rabbits_farm.data.converters

import com.kudashov.rabbits_farm.data.domain.*
import com.kudashov.rabbits_farm.data.dto.*

interface FarmConverter {
    fun convertRabbitFromApiToDomain(rabbitDto: RabbitDto): RabbitDomain

    fun convertRabbitMoreInfFromApiToDomain(rabbit: RabbitMoreInfDto): RabbitMoreInfDomain

    fun convertCageFromApiToDomain(cageDto: CageDto): CageDomain

    fun convertBreedFromApiToDomain(breedDto: BreedDto): BreedDomain

    fun convertOperationFromApiToDomain(operationDto: OperationDto): OperationDomain
}