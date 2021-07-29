package com.kudashov.rabbits_farm.screens.farm.filters

class CageFilter {
    companion object {
        var farmNumber: Int? = null
        var type: String? = null
        var isParallel: Int? = null
        var countOfRabbit: Int? = null
        var status: MutableList<String> = ArrayList()
        var orderBy: String? = null

        override fun toString(): String {
            return "farmNumber " +farmNumber.toString() + "\n" +
                    "type " + type + "\n" +
                    "isParallel " + isParallel + "\n" +
                    "countOfRabbit " + countOfRabbit.toString() + "\n" +
                    "status " + status.toString() + "\n" +
                    "orderBy" + orderBy.toString()
        }

        fun throwOff() {
            farmNumber = null
            status.clear()
            type = null
            isParallel = null
            countOfRabbit = null
        }
    }
}