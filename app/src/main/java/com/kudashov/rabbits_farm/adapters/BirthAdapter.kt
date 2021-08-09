package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.BirthDelegate
import com.kudashov.rabbits_farm.adapters.viewHolders.BirthViewHolder
import com.kudashov.rabbits_farm.data.domain.BirthListItem
import com.kudashov.rabbits_farm.data.domain.BirthListItemTypes

class BirthAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfData: MutableList<BirthListItemTypes> = ArrayList()
    private var delegate: BirthDelegate? = null

    fun attachDelegate(delegate: BirthDelegate) {
        this.delegate = delegate
    }

    fun setList(list: List<BirthListItemTypes>) {
        listOfData.clear()
        listOfData.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listOfData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listOfData[position]) {
            is BirthListItem -> 0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BirthViewHolder -> holder.bind(listOfData[position] as BirthListItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BirthViewHolder(inflater.inflate(R.layout.item_birth, parent, false), delegate)
    }
}