package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.ui.Kill

class KillViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
    private val weight: TextView = view.findViewById(R.id.txt_weight)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var kill: Kill
    private var isDone: Boolean = false

    init {
        btnDone.setOnClickListener {
            if (isDone){

            }
        }
    }

    fun bind(kill: Kill) {
        this.kill = kill

        data.text = kill.data
        numberOfCage.text = kill.numberOfCage
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