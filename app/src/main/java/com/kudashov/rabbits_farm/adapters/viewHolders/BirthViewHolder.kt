package com.kudashov.rabbits_farm.adapters.viewHolders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.BirthDelegate
import com.kudashov.rabbits_farm.data.ui.BirthListItem

class BirthViewHolder(val view: View, private val delegate: BirthDelegate?) :
    RecyclerView.ViewHolder(view) {
    private val countOfDay: TextView = view.findViewById(R.id.txt_count_of_day)
    private val numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
    private val isConfirmed: ImageView = view.findViewById(R.id.ic_status)

    private val btnFertilizes: Button = view.findViewById(R.id.btn_fertilized)
    private val btnNotFertilized: Button = view.findViewById(R.id.btn_not_fertilized)
    private val btnTakeBirths: Button = view.findViewById(R.id.btn_take_births)

    private lateinit var birthListItem: BirthListItem

    init {
        btnTakeBirths.setOnClickListener {
            delegate?.openBirthDialog()
        }

        btnFertilizes.setOnClickListener {

        }
        btnNotFertilized.setOnClickListener {

        }
    }

    fun bind(birthListItem: BirthListItem) {
        this.birthListItem = birthListItem

        countOfDay.text = birthListItem.durationPregnancy
        numberOfCage.text = birthListItem.numberOfCage

        if (birthListItem.isConfirmed) {
            isConfirmed.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.ic_confirmed))
            btnTakeBirths.visibility = View.VISIBLE

            btnFertilizes.visibility = View.GONE
            btnNotFertilized.visibility = View.GONE
        } else {
            isConfirmed.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.ic_not_confirmed))
            btnTakeBirths.visibility = View.GONE
            btnFertilizes.visibility = View.VISIBLE
            btnNotFertilized.visibility = View.VISIBLE
        }
    }
}