package com.kudashov.rabbits_farm.adapters.viewHolders.farm

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.FarmDelegate
import com.kudashov.rabbits_farm.data.ui.RabbitItem

class RabbitViewHolder(var view: View, var delegate: FarmDelegate?) :
    RecyclerView.ViewHolder(view) {

    private val number: TextView = view.findViewById(R.id.str_number_of_cage)
    private val age: TextView = view.findViewById(R.id.str_age)
    private val gender: ImageView = view.findViewById(R.id.gender)
    private val type: TextView = view.findViewById(R.id.str_type)

    private lateinit var rabbitItem: RabbitItem

    init {
        view.setOnClickListener {
            delegate?.openMoreRabbitInfo(rabbitItem)
        }
    }

    fun bind(rabbitItem: RabbitItem) {
        this.rabbitItem = rabbitItem

        number.text = rabbitItem.numberOfCage
        age.text = rabbitItem.age
        type.text = rabbitItem.type

        if (rabbitItem.isMale) {
            gender.setImageDrawable(
                AppCompatResources.getDrawable(
                    view.context,
                    R.drawable.ic_gender_male_black
                )
            )
        } else {
            gender.setImageDrawable(
                AppCompatResources.getDrawable(
                    view.context,
                    R.drawable.ic_gender_female_black
                )
            )
        }
    }
}