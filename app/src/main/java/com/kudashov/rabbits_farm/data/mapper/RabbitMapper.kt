package com.kudashov.rabbits_farm.data.mapper

import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.utilits.statuses.rabbit.TypeOfRabbit

class RabbitMapper {
    companion object {
        fun fromApiToListRabbitItem(list: List<RabbitDto>): List<Rabbit> {
            val newList: MutableList<Rabbit> = ArrayList()
            for (element in list) {
                newList.add(
                    fromApiToRabbitItem(element)
                )
            }
            return newList
        }

        private fun fromApiToRabbitItem(rabbitDto: RabbitDto): Rabbit =
            Rabbit(
                rabbitDto.id,
                getNumberOfCage(
                    rabbitDto.cage
                ),
                getAge(
                    rabbitDto.birthday
                ),
                rabbitDto.is_male,
                getType(
                    rabbitDto.current_type
                )
            )


        private fun getNumberOfCage(cage: CageDto): String {
            return cage.farm_number.toString() + cage.number.toString() + cage.letter
        }

        private fun getType(currentType: String): String {
            return when(currentType){
                TypeOfRabbit.baby -> "Малыш"
                TypeOfRabbit.death -> "Мертвый"
                TypeOfRabbit.fattening -> "Откорм."
                TypeOfRabbit.mather -> "Самка"
                TypeOfRabbit.father -> "Самец"
                else -> "???"
            }
        }

        private fun getAge(birthday: String): String {
            // todo - сделать вычисление возраста
            return "???" + " дн"
        }
    }
}