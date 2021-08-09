package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.compose.ui.res.stringResource
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.data.domain.DepositionFromMotherDomain

class DepositionFromMotherViewHolder(val view: View, val delegate: TaskDelegate?) :
    RecyclerView.ViewHolder(view) {
    private val date: TextView = view.findViewById(R.id.data)
    private val cageFrom: TextView = view.findViewById(R.id.txt_from)
    private val cageTo: TextView = view.findViewById(R.id.txt_to)

    private val countOfMale: SeekBar = view.findViewById(R.id.sb_count_of_male)
    private val txtCountOfMale: TextView = view.findViewById(R.id.txt_sb_count_of_male)
    private val txtCountOfFemale: TextView = view.findViewById(R.id.txt_sb_count_of_female)
    private val countOfFemale: SeekBar = view.findViewById(R.id.sb_count_of_female)

    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var deposition: DepositionFromMotherDomain
    private var isDone: Boolean = false
    private var countBunnies: Int? = null

    init {
        countOfMale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                txtCountOfMale.text = seekBar?.progress.toString()

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    countOfFemale.setProgress(
                        countBunnies?.minus(seekBar?.progress ?: 0) ?: 0,
                        true
                    )
                else countOfFemale.progress = countBunnies?.minus(seekBar?.progress ?: 0) ?: 0

                txtCountOfFemale.text =
                    (countBunnies?.minus(seekBar?.progress ?: 0) ?: 0).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        countOfFemale.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtCountOfFemale.text = seekBar?.progress.toString()

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    countOfMale.setProgress(
                        countBunnies?.minus(seekBar?.progress ?: 0) ?: 0,
                        true
                    )
                else countOfMale.progress = countBunnies?.minus(seekBar?.progress ?: 0) ?: 0

                txtCountOfMale.text =
                    (countBunnies?.minus(seekBar?.progress ?: 0) ?: 0).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnDone.setOnClickListener {
            if (!isDone) {
                delegate?.confirmDepositionFromMotherTask(deposition.id, txtCountOfMale.text.toString().toInt())
            }
        }
    }

    fun bind(deposition: DepositionFromMotherDomain) {
        this.deposition = deposition

        date.text = deposition.date
        cageFrom.text = view.context.getString(
            R.string.task_item_cage_format,
            deposition.cageFrom.farmNumber,
            deposition.cageFrom.cageNumber,
            deposition.cageFrom.letter
        )
        cageTo.text = view.context.getString(
            R.string.task_item_deposition_from_mother_txt_cage_to,
            deposition.maleCageTo.farmNumber,
            deposition.maleCageTo.cageNumber,
            deposition.maleCageTo.letter,
            deposition.femaleCageTo.farmNumber,
            deposition.femaleCageTo.cageNumber,
            deposition.femaleCageTo.letter
        )

        isDone = deposition.isDone
        countBunnies = deposition.countBunnies

        countOfMale.max = deposition.countBunnies
        countOfMale.progress = 0
        countOfFemale.max = deposition.countBunnies
        countOfFemale.progress = deposition.countBunnies

        if (deposition.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}