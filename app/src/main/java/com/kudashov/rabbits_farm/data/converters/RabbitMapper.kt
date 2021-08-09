package com.kudashov.rabbits_farm.data.converters

import android.os.Build
import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.domain.RabbitItem
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfUi
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList

class RabbitMapper {
    companion object {
        private val TAG: String? = this::class.java.simpleName

        fun fromApiToListItem(list: List<RabbitDto>): List<RabbitItem> {
            val newList: MutableList<RabbitItem> = ArrayList()
            for (element in list) {
                newList.add(
                    fromApiToItem(element)
                )
            }
            return newList
        }

        private fun fromApiToItem(rabbitDto: RabbitDto): RabbitItem = RabbitItem(
                rabbitDto.id,
                getNumberOfCage(rabbitDto.cage),
                getAge(rabbitDto.birthday),
                rabbitDto.is_male,
                getType(rabbitDto.current_type)
            )
        private fun getNumberOfCage(cage: CageDto): String {
            return cage.farm_number.toString() + "-" + cage.number.toString() + "(${cage.letter})"
        }
        private fun getType(currentType: String): String {
            return when (currentType) {
                RABBIT_TYPE_BABY -> "Малыш"
                RABBIT_TYPE_DEATH -> "Мертвый"
                RABBIT_TYPE_FATTENING -> "Откорм."
                RABBIT_TYPE_MATHER -> "Самка"
                RABBIT_TYPE_FATHER -> "Самец"
                else -> "???"
            }
        }
        private fun getAge(birthday: String?): String {
            return if (birthday != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val today = dateFormat.format(Date())

                val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val difference = ChronoUnit.DAYS.between(
                    LocalDate.parse(birthday.substring(0, 10), format),
                    LocalDate.parse(today.substring(0, 10), format)
                )

                "$difference"
            } else "???"
        }

        fun fromApiToRabbitUi(rabbit: RabbitMoreInfDto) = RabbitMoreInfUi(
            rabbit.id,
            rabbit.is_male,
            getBirthday(rabbit.birthday),
            getAge(rabbit.birthday),
            rabbit.breed,
            rabbit.current_type,
            getType(rabbit.current_type),
            rabbit.cage,
            getStatuses(rabbit.status),
            rabbit.output,
            rabbit.output_efficiency,
            rabbit.weight
        )

        private fun getBirthday(birthdayIso: String?): String {
            return if (birthdayIso != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val birthday = LocalDate.parse(birthdayIso.substring(0, 10), format)

                "$birthday"
            } else "???"
        }

        private fun getStatuses(statuses: List<String>): String {
            var res = ""
            for (i in STATUSES_RABBIT) {
                if (statuses.contains(i.first)) res += "\n" + i.second
            }
            if (res.isEmpty()) res + "Статус не определен"
            return res
        }
    }
}