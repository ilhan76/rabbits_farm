package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.res.stringResource
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.BirthListItem
import com.kudashov.rabbits_farm.data.BirthListItemTypes
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.RH

class BirthAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfData: MutableList<BirthListItemTypes> = ArrayList()

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
        when(holder){
            is BirthViewHolder -> holder.bind(listOfData[position] as BirthListItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BirthViewHolder(inflater.inflate(R.layout.item_birth, parent, false))

    }

    class BirthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var countOfDay: TextView = view.findViewById(R.id.txt_count_of_day)
        var numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
        var isConfirmed: ImageView = view.findViewById(R.id.ic_status)

        var btnFertilizes: Button = view.findViewById(R.id.btn_fertilized)
        var btnNotFertilized: Button = view.findViewById(R.id.btn_not_fertilized)
        var btnTakeBirths: Button = view.findViewById(R.id.btn_take_births)

        fun bind(birthListItem: BirthListItem) {
            countOfDay.text = birthListItem.countOfDay
            numberOfCage.text = birthListItem.numberOfCage

            if (birthListItem.status == RH.string(R.string.birth_item_txt_confirmed)) {
                isConfirmed.setImageDrawable(RH.drawable(R.drawable.ic_confirmed))
                btnTakeBirths.visibility = View.VISIBLE
                btnFertilizes.visibility = View.GONE
                btnNotFertilized.visibility = View.GONE
            } else {
                isConfirmed.setImageDrawable(RH.drawable(R.drawable.ic_not_confirmed))
                btnTakeBirths.visibility = View.GONE
                btnFertilizes.visibility = View.VISIBLE
                btnNotFertilized.visibility = View.VISIBLE
            }
        }
    }
}