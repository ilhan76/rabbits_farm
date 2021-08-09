package com.kudashov.rabbits_farm.data.converters

import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.domain.OperationItem
import com.kudashov.rabbits_farm.utilits.const.OPERATIONS

class OperationMapper {
    companion object {

        fun fromApiToListItem(list: List<OperationDto>): List<OperationItem> {
            val newList: MutableList<OperationItem> = ArrayList()
            for (element in list) {
                newList.add(
                    fromApiToItem(element)
                )
            }
            return newList
        }

        private fun fromApiToItem(operationDto: OperationDto): OperationItem = OperationItem(
            operationDto.rabbit_id,
            operationDto.time.substring(0, 10),
            getOperation(operationDto.type)
        )

        private fun getOperation(type: String): String {
            return OPERATIONS[type] ?: "???"
        }
    }
}