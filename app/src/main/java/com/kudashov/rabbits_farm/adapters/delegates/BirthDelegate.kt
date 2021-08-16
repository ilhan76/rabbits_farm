package com.kudashov.rabbits_farm.adapters.delegates

interface BirthDelegate{
    fun confirmPregnancy(id: Int, isConfirmed: Boolean)
    fun takeBirth(id: Int)
}