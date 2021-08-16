package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.viewHolders.tasks.TaskWeightViewHolder
import com.kudashov.rabbits_farm.data.domain.WeightListItem

class TaskWeightAdapter : RecyclerView.Adapter<TaskWeightViewHolder>() {
    private val list: MutableList<WeightListItem> = ArrayList()

    fun setList(newList: List<WeightListItem>) {
        list.clear()
        list.addAll(newList)

        notifyDataSetChanged()
    }

    fun getList(): List<WeightListItem> {
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskWeightViewHolder {
        return TaskWeightViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_weight, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskWeightViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}