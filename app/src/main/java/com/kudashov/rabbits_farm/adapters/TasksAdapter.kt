package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.TaskDelegate
import com.kudashov.rabbits_farm.adapters.viewHolders.tasks.*
import com.kudashov.rabbits_farm.data.domain.*

class TasksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfTasks: MutableList<TaskListItemType> = ArrayList()
    private var delegate: TaskDelegate? = null

    fun setList(list: List<TaskListItemType>) {
        listOfTasks.clear()
        listOfTasks.addAll(list)

        notifyDataSetChanged()
    }

    fun attachDelegate(delegate: TaskDelegate) {
        this.delegate = delegate
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }

    override fun getItemViewType(position: Int): Int {
        // todo - изменить
        return when (listOfTasks[position]) {
            is DepositionFromMotherDomain -> 0
            is VaccinationDomain -> 1
            is InspectionDomain -> 2
            is ReproductionDomain -> 3
            is KillDomain -> 4
            is DepositionToFatteningDomain -> 5
            is DepositionToReproductionDomain -> 6
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DepositionFromMotherViewHolder -> holder.bind(listOfTasks[position] as DepositionFromMotherDomain)
            is VaccinationViewHolder -> holder.bind(listOfTasks[position] as VaccinationDomain)
            is InspectionViewHolder -> holder.bind(listOfTasks[position] as InspectionDomain)
            is ReproductionViewHolder -> holder.bind(listOfTasks[position] as ReproductionDomain)
            is KillViewHolder -> holder.bind(listOfTasks[position] as KillDomain)
            is DepositionToFatteningViewHolder -> holder.bind(listOfTasks[position] as DepositionToFatteningDomain)
            is DepositionToReproductionViewHolder -> holder.bind(listOfTasks[position] as DepositionToReproductionDomain)
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
                ), delegate
            )
            2 -> InspectionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_inspection,
                    parent,
                    false
                ), delegate
            )
            3 -> ReproductionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_reproduction,
                    parent,
                    false
                ), delegate
            )
            4 -> KillViewHolder(inflater.inflate(R.layout.item_tasks_kill, parent, false), delegate)
            5 -> DepositionToFatteningViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_deposition_to_fattening,
                    parent,
                    false
                ), delegate
            )
            6 -> DepositionToReproductionViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_deposition_to_reproduction,
                    parent,
                    false
                ), delegate
            )
            else -> DepositionFromMotherViewHolder(
                inflater.inflate(
                    R.layout.item_tasks_deposition_from_mother,
                    parent,
                    false
                ), delegate
            )
        }
    }
}