package com.kudashov.rabbits_farm.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kudashov.rabbits_farm.R

class SpinnerAdapter(context: Context) : BaseAdapter() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val list: MutableList<String> = ArrayList()

    fun setList(newList: List<String>){
        list.clear()
        list.addAll(newList)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = layoutInflater.inflate(R.layout.spinner_item, parent, false)

        val txtFarm: TextView = view.findViewById(R.id.spinner_item_number_of_farm)

        txtFarm.text = list[position]

        return view
    }
}