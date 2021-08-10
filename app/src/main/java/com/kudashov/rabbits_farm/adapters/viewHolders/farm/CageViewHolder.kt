package com.kudashov.rabbits_farm.adapters.viewHolders.farm

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.domain.CageDomain

class CageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val label: ImageView = view.findViewById(R.id.image_cage_label)
    private val numberOfFarm: TextView = view.findViewById(R.id.str_number_of_farm)
    private val number: TextView = view.findViewById(R.id.str_number)
    private val typeOfCage: TextView = view.findViewById(R.id.str_type_of_cage)
    private val status: TextView = view.findViewById(R.id.str_status_of_cage)

    private lateinit var cageDomain: CageDomain

    init {
        view.setOnClickListener {

        }
    }

    fun bind(cageDomain: CageDomain) {
        this.cageDomain = cageDomain

        numberOfFarm.text = cageDomain.numberOfFarm
        number.text = cageDomain.numberOfCage
        typeOfCage.text = cageDomain.type
        status.text = cageDomain.status
    }
}