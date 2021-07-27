package com.kudashov.rabbits_farm.data.mapper

import android.os.Build
import android.util.Log
import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.utilits.statuses.rabbit.TypeOfRabbit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList

class RabbitMapper {
    companion object {
        private val TAG: String? = this::class.java.simpleName

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
            return when (currentType) {
                TypeOfRabbit.baby -> "Малыш"
                TypeOfRabbit.death -> "Мертвый"
                TypeOfRabbit.fattening -> "Откорм."
                TypeOfRabbit.mather -> "Самка"
                TypeOfRabbit.father -> "Самец"
                else -> "???"
            }
        }

        private fun getAge(birthday: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val today = dateFormat.format(Date())

                val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val difference = ChronoUnit.DAYS.between(
                    LocalDate.parse(birthday.substring(0, 10), format),
                    LocalDate.parse(today.substring(0, 10), format)
                )

                "$difference дн"
            } else "??? дн"
        }
    }
}