package com.kudashov.rabbits_farm.data.mapper

import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.item.Cage

class CageMapper {
    companion object {
        fun fromApiToListCageItem(list: List<CageDto>): List<Cage> {
            val newList: MutableList<Cage> = ArrayList()
            for (element in list) {
                newList.add(
                    fromApiToCageItem(element)
                )
            }
            return newList
        }

        private fun fromApiToCageItem(cageDto: CageDto): Cage =
            Cage(
                cageDto.id,
                getNumberOfCage(cageDto),
                cageDto.farm_number.toString(),
                getCageType(cageDto),
                getCageStatus(cageDto.status)
            )

        private fun getNumberOfCage(cageDto: CageDto): String {
            return cageDto.farm_number.toString() + cageDto.number.toString() + cageDto.letter
        }

        private fun getCageType(cageDto: CageDto): String {
            return when(cageDto.type){
                "M" -> "МАТ"
                "F" -> "ОТКОРМ"
                else -> "???"
            }
        }

        private fun getCageStatus(list: List<String>): String {
            var status = ""

            if (list.contains("C")) status += "Уб."
            if (list.contains("R")) status += " Рем."

            return status
        }
    }
}