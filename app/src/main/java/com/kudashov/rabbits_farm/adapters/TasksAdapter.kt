package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.viewHolders.tasks.*
import com.kudashov.rabbits_farm.data.domain.*

class TasksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfTasks: MutableList<TaskListItemType> = ArrayList()

    fun setList(list: List<TaskListItemType>) {
        listOfTasks.clear()
        listOfTasks.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listOfTasks[position]) {
            is DepositionFromMotherDomain -> 0
            is VaccinationDomain -> 1
            is InspectionDomain -> 2
            is ReproductionDomain -> 3
            is KillDomain -> 4
            is DepositionDomain -> 5
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DepositionFromMotherViewHolder -> holder.bind(listOfTasks[position] as DepositionFromMotherDomain)
            is VaccinationViewHolder -> holder.bind(listOfTasks[position] as VaccinationDomain)
            is InspectionViewHolder -> holder.bind(listOfTasks[position] as InspectionDomain)
            is ReproductionViewHolder -> holder.bind(listOfTasks[position] as ReproductionDomain)
            is KillViewHolder -> holder.bind(listOfTasks[position] as KillDomain)
            is DepositionViewHolder -> holder.bind(listOfTasks[position] as DepositionDomain)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> VaccinationViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_vaccination,
                    parent,
                    false
                )
            )
            2 -> InspectionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_inspection,
                    parent,
                    false
                )
            )
            3 -> ReproductionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_reproduction,
                    parent,
                    false
                )
            )
            4 -> KillViewHolder(inflater.inflate(R.layout.item_tasks_kill, parent, false))
            5 -> DepositionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_deposition,
                    parent,
                    false
                )
            )
            else -> DepositionFromMotherViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_deposition_from_mother,
                    parent,
                    false
                )
            )
        }
    }
}