package com.kudashov.rabbits_farm.data.ui

sealed class TasksListItemTypes

data class Deposition(
        var data: String,
        var numberOfCageFrom: String?,
        var numberOfCageTo: String,
        var isDone: Boolean
) : TasksListItemTypes()

data class Vaccination(
        var data: String,
        var numberOfCage: String,
        var isDone: Boolean
) : TasksListItemTypes()

data class Inspection(
        var data: String,
        var numberOfCage: String,
        var weight: Double? = null,
        var isDone: Boolean
) : TasksListItemTypes()

data class Reproduction(
        var data: String,
        var takeFemaleFrom: String,
        var takeFemaleTo: String,
        var isDone: Boolean
) : TasksListItemTypes()

data class Kill(
        var data: String,
        var numberOfCage: String,
        var weight: Double?,
        var isDone: Boolean
) : TasksListItemTypes()