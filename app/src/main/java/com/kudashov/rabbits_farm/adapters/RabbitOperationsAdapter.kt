package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.dto.OperationDto

class RabbitOperationsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<OperationDto> = ArrayList()

    fun setList(newList: List<OperationDto>){
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

    class RabbitOperationViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val time: TextView = view.findViewById(R.id.txt_data)
        private val event: TextView = view.findViewById(R.id.txt_event)

        fun bind(operation: OperationDto){
            time.text = operation.time.substring(0, 10)
            event.text = operation.type
            //todo - мапинг операций
        }
    }
}

