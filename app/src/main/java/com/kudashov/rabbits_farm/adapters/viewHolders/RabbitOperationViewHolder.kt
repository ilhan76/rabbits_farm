package com.kudashov.rabbits_farm.adapters.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.domain.OperationItem

class RabbitOperationViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val time: TextView = view.findViewById(R.id.txt_data)
    private val event: TextView = view.findViewById(R.id.txt_event)

    fun bind(operation: OperationItem){
        time.text = operation.time
        event.text = operation.type
    }
}