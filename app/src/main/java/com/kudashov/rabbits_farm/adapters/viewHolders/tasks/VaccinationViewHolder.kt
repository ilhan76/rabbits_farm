package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.data.domain.VaccinationDomain

class VaccinationViewHolder(val view: View, val delegate: TaskDelegate?) : RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var vaccination: VaccinationDomain
    private var isDone: Boolean = false

    init {
        btnDone.setOnClickListener {
            if (!isDone){
                delegate?.confirmSimpleTask(vaccination.id)
            }
        }
    }

    fun bind(vaccination: VaccinationDomain) {
        this.vaccination = vaccination

        data.text = vaccination.date
        numberOfCage.text = view.context.getString(
            R.string.task_item_cage_format,
            vaccination.cage.farmNumber,
            vaccination.cage.cageNumber,
            vaccination.cage.letter
        )
        isDone = vaccination.isDone

        if (vaccination.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}