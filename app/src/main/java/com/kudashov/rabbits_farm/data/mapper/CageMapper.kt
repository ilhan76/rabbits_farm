package com.kudashov.rabbits_farm.data.mapper

import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.item.Cage
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_CLEAN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_REPAIR
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_TYPE_FATTENING
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_TYPE_MOTHER

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
                CAGE_TYPE_MOTHER -> {
                    if (cageDto.is_parallel) "МАТ ||"
                    else "МАТ |"
                }
                CAGE_TYPE_FATTENING -> "ОТКОРМ"
                else -> "???"
            }
        }

        private fun getCageStatus(list: List<String>): String {
            var status = ""

            if (list.contains(CAGE_STATUS_NEED_CLEAN)) status += "Уб."
            if (list.contains(CAGE_STATUS_NEED_REPAIR)) status += " Рем."
            if (status.isEmpty()) status += "-----"
            return status
        }
    }
}