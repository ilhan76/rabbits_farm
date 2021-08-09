package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.viewHolders.RabbitOperationViewHolder
import com.kudashov.rabbits_farm.data.domain.OperationItem

class RabbitOperationsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<OperationItem> = ArrayList()

    fun setList(newList: List<OperationItem>){
        list.clear()
        list.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RabbitOperationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rabbit_history, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RabbitOperationViewHolder) holder.bind(list[position])
    }
}

