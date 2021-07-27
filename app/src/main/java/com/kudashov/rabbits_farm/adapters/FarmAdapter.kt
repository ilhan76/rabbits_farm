package com.kudashov.rabbits_farm.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.delegates.FarmDelegate
import com.kudashov.rabbits_farm.data.item.AboutFarmListItemType
import com.kudashov.rabbits_farm.data.item.Cage
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.squareup.picasso.Picasso
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
            is Rabbit -> 1
            is Cage -> 2
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RabbitViewHolder -> {
                holder.bind(listData[position] as Rabbit)
            }
            is CageViewHolder -> holder.bind(listData[position] as Cage)
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

    class RabbitViewHolder(var view: View, var delegate: FarmDelegate?) :
        RecyclerView.ViewHolder(view) {

        private val label: ImageView = view.findViewById(R.id.image_rabbit_label)
        private val number: TextView = view.findViewById(R.id.str_number_of_cage)
        private val age: TextView = view.findViewById(R.id.str_age)
        private val gender: ImageView = view.findViewById(R.id.gender)
        private val type: TextView = view.findViewById(R.id.str_type)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(rabbit: Rabbit) {
            number.text = rabbit.numberOfCage
            age.text = rabbit.age
            type.text = rabbit.type

            if (rabbit.isMale){
                gender.setImageDrawable(view.resources.getDrawable(R.drawable.ic_gender_male_black))
            } else {
                gender.setImageDrawable(view.resources.getDrawable(R.drawable.ic_gender_female_black))
            }
            view.setOnClickListener {
                delegate?.openMoreRabbitInfo(rabbit)
            }
        }
    }

    class CageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val label: ImageView = view.findViewById(R.id.image_cage_label)
        private val numberOfFarm: TextView = view.findViewById(R.id.str_number_of_farm)
        private val number: TextView = view.findViewById(R.id.str_number)
        private val typeOfCage: TextView = view.findViewById(R.id.str_type_of_cage)
        private val status: TextView = view.findViewById(R.id.str_status_of_cage)

        fun bind(cage: Cage) {
            numberOfFarm.text = cage.numberOfFarm
            number.text = cage.numberOfCage
            typeOfCage.text = cage.type
            status.text = cage.status
        }
    }
}