package com.kudashov.rabbits_farm.data.domain

import com.kudashov.rabbits_farm.data.dto.CageSimpleDto

sealed class TaskListItemType

data class DepositionDomain(
        val id: Int,
        val type: String,
        var date: String,
        val userId: Int,
        val cageFrom: CageSimpleDto,
        val cageTo: CageSimpleDto,
        val isDone: Boolean
) : TaskListItemType()

data class DepositionFromMotherDomain(
        val id: Int,
        val type: String,
        val date: String,
        val userId: Int,
        val cageFrom: CageSimpleDto,
        val maleCageTo: CageSimpleDto,
        val femaleCageTo: CageSimpleDto,
        val countBunnies: Int,
        val isDone: Boolean
) : TaskListItemType()

data class ReproductionDomain(
        val id: Int,
        val type: String,
        var date: String,
        val userId: Int,
        val cageFrom: CageSimpleDto,
        val cageTo: CageSimpleDto,
        val isDone: Boolean
) : TaskListItemType()

data class VaccinationDomain(
        val id: Int,
        val type: String,
        var date: String,
        val userId: Int,
        val cage: CageSimpleDto,
        val isDone: Boolean
) : TaskListItemType()

data class InspectionDomain(
        val id: Int,
        val type: String,
        var date: String,
        val userId: Int,
        val cage: CageSimpleDto,
        val countRabbit: Int,
        val isDone: Boolean
) : TaskListItemType()

data class KillDomain(
        val id: Int,
        val type: String,
        var date: String,
        val userId: Int,
        val cage: CageSimpleDto,
        val weight: Double,
        val isDone: Boolean
) : TaskListItemType()