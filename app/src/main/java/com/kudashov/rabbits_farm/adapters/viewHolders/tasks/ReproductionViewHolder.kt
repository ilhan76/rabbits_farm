package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.ui.Reproduction

class ReproductionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val takeFrom: TextView = view.findViewById(R.id.txt_from)
    private val takeTo: TextView = view.findViewById(R.id.txt_to)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var reproduction: Reproduction
    private var isDone: Boolean = false

    init {
        btnDone.setOnClickListener {
            if (isDone){

            }
        }
    }

    fun bind(reproduction: Reproduction) {
        this.reproduction = reproduction

        data.text = reproduction.data
        takeFrom.text = reproduction.takeFemaleFrom
        takeTo.text = reproduction.takeFemaleTo
        isDone = reproduction.isDone

        if (reproduction.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}