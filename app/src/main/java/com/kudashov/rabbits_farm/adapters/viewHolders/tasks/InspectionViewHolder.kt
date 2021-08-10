package com.kudashov.rabbits_farm.adapters.viewHolders.tasks

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.TaskWeightAdapter
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.data.domain.InspectionDomain
import com.kudashov.rabbits_farm.data.domain.WeightListItem

class InspectionViewHolder(val view: View, val delegate: TaskDelegate?) :
    RecyclerView.ViewHolder(view) {
    private val data: TextView = view.findViewById(R.id.data)
    private val numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
    private val rvWeight: RecyclerView = view.findViewById(R.id.rvWeight)
    private var adapter: TaskWeightAdapter = TaskWeightAdapter()

    // todo - список для взвешивания
    private val btnDone: Button = view.findViewById(R.id.btn_done)

    private lateinit var inspection: InspectionDomain
    private var isDone: Boolean = false

    init {
        rvWeight.layoutManager = LinearLayoutManager(view.context)
        rvWeight.adapter = adapter

        btnDone.setOnClickListener {
            if (!isDone) {
                Log.d("IVH", "onClick: ${adapter.getList()}")
                val list: MutableList<Double> = ArrayList()
                for (i in adapter.getList()){
                    if (i.weight != null) list.add(i.weight!!)
                }
                Log.d("IVH", "onClick: $list")
                Log.d("IVH", "onClick: ${list.size == inspection.countRabbit}")
                Log.d("IVH", "onClick: ${list.size}")
                Log.d("IVH", "onClick: ${inspection.countRabbit}")
                if (list.size == inspection.countRabbit){
                    delegate?.confirmSlaughterInspectionTask(inspection.id, list)
                } else {
                    delegate?.confirmSlaughterInspectionTask(inspection.id, listOf())
                }
            }
        }
    }

    fun bind(inspection: InspectionDomain) {
        this.inspection = inspection

        val list: MutableList<WeightListItem> = ArrayList()
        for (i in 1..inspection.countRabbit)
            list.add(WeightListItem())

        adapter.setList(list)

        data.text = inspection.date
        numberOfCage.text = view.context.getString(
            R.string.task_item_cage_format,
            inspection.cage.farmNumber,
            inspection.cage.cageNumber,
            inspection.cage.letter
        )
        isDone = inspection.isDone

        if (inspection.isDone) {
            btnDone.setText(R.string.task_item_btn_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_green)
        } else {
            btnDone.setText(R.string.task_item_btn_not_yet_done)
            btnDone.setBackgroundResource(R.drawable.shape_btn_red)
        }
    }
}