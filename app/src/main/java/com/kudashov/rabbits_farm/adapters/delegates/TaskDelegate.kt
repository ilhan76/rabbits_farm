package com.kudashov.rabbits_farm.adapters.delegates

interface TaskDelegate {
    fun confirmSimpleTask(id: Int)
    fun confirmSlaughterInspectionTask(id: Int, weights: List<Double>)
    fun confirmDepositionFromMotherTask(id: Int, countMales: Int)
}