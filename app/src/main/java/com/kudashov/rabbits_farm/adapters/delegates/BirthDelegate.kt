package com.kudashov.rabbits_farm.adapters.delegates

interface BirthDelegate{
    fun openBirthDialog()
    fun confirmPregnancy(id: Int, isConfirmed: Boolean)
    fun takeBirth(id: Int, bornBunnies: Int)
}