package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.data.domain.KillDomain

class KillViewHolder(val view: View, val delegate: TaskDelegate?) : RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
    private val weight: TextView = view.findViewById(R.id.edit_txt_weight)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var kill: KillDomain
    private var isDone: Boolean = false

    init {
        btnDone.setOnClickListener {
            if (!isDone){
                delegate?.confirmSimpleTask(kill.id)
            }
        }
    }

    fun bind(kill: KillDomain) {
        this.kill = kill

        data.text = kill.date
        numberOfCage.text = view.context.getString(
            R.string.task_item_cage_format,
            kill.cage.farmNumber,
            kill.cage.cageNumber,
            kill.cage.letter
        )
        weight.text = view.context.getString(
            R.string.task_item_weight_format,
            kill.weight.toString()
        )
        isDone = kill.isDone

        if (kill.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}