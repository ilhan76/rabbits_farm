package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.ui.Deposition

class DepositionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val cageFrom: TextView = view.findViewById(R.id.txt_from)
    private val cageTo: TextView = view.findViewById(R.id.txt_to)

    private val countOfMale: SeekBar = view.findViewById(R.id.sb_count_of_male)
    private val txtCountOfMale: TextView = view.findViewById(R.id.txt_sb_count_of_male)
    private val txtCountOfFemale: TextView = view.findViewById(R.id.txt_sb_count_of_female)
    private val countOfFemale: SeekBar = view.findViewById(R.id.sb_count_of_female)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var deposition: Deposition
    private var isDone: Boolean = false

    init {
        countOfMale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                txtCountOfMale.text = seekBar?.progress.toString()
            }
        })
        countOfFemale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                txtCountOfFemale.text = seekBar?.progress.toString()
            }
        })

        btnDone.setOnClickListener {
            if (isDone){

            }
        }
    }

    fun bind(deposition: Deposition) {
        this.deposition = deposition

        data.text = deposition.data
        cageFrom.text = deposition.numberOfCageFrom
        cageTo.text = deposition.numberOfCageTo

        txtCountOfMale.text = "0"
        txtCountOfFemale.text = "0"
        isDone = deposition.isDone

        if (deposition.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}