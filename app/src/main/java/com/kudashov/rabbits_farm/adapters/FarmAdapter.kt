package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.FarmDelegate
import com.kudashov.rabbits_farm.adapters.viewHolders.farm.CageViewHolder
import com.kudashov.rabbits_farm.adapters.viewHolders.farm.RabbitViewHolder
import com.kudashov.rabbits_farm.data.ui.AboutFarmListItemType
import com.kudashov.rabbits_farm.data.ui.CageItem
import com.kudashov.rabbits_farm.data.ui.RabbitItem
import java.util.*

class FarmAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData: MutableList<AboutFarmListItemType> = LinkedList()
    private var delegate: FarmDelegate? = null

    fun attachDelegate(delegate: FarmDelegate) {
        this.delegate = delegate
    }

    fun setList(list: List<AboutFarmListItemType>) {
        listData.clear()
        listData.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (listData[position]) {
            is RabbitItem -> 1
            is CageItem -> 2
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RabbitViewHolder -> {
                holder.bind(listData[position] as RabbitItem)
            }
            is CageViewHolder -> holder.bind(listData[position] as CageItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> RabbitViewHolder(
                inflater.inflate(R.layout.item_farm_rabbit, parent, false),
                delegate
            )
            else -> CageViewHolder(inflater.inflate(R.layout.item_farm_cage, parent, false))
        }
    }
}