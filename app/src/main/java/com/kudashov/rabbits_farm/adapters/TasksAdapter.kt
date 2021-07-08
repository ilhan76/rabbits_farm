package com.kudashov.rabbits_farm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.*

class TasksAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfTasks: MutableList<TasksListItemTypes> = ArrayList()

    fun setList(list: List<TasksListItemTypes>) {
        listOfTasks.clear()
        listOfTasks.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listOfTasks.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(listOfTasks[position]){
            is Deposition -> 0
            is Vaccination -> 1
            is Inspection -> 2
            is Reproduction -> 3
            is Kill -> 4
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DepositionViewHolder -> holder.bind(listOfTasks[position] as Deposition)
            is VaccinationViewHolder -> holder.bind(listOfTasks[position] as Vaccination)
            is InspectionViewHolder -> holder.bind(listOfTasks[position] as Inspection)
            is ReproductionViewHolder -> holder.bind(listOfTasks[position] as Reproduction)
            is KillViewHolder -> holder.bind(listOfTasks[position] as Kill)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            1 -> VaccinationViewHolder(inflater.inflate(R.layout.item_tasks_vaccination, parent, false))
            2 -> InspectionViewHolder(inflater.inflate(R.layout.item_tasks_inspection, parent, false))
            3 -> ReproductionViewHolder(inflater.inflate(R.layout.item_tasks_reproduction, parent, false))
            4 -> KillViewHolder(inflater.inflate(R.layout.item_tasks_kill, parent, false))
            else -> DepositionViewHolder(inflater.inflate(R.layout.item_tasks_deposition, parent, false))
        }
    }

    class DepositionViewHolder(view: View): RecyclerView.ViewHolder(view){
        var data: TextView = view.findViewById(R.id.data)
        var cageFrom: TextView = view.findViewById(R.id.txt_from)
        var cageTo: TextView = view.findViewById(R.id.txt_to)

        var countOfMale: SeekBar = view.findViewById(R.id.sb_count_of_male)
        var countOfFemale: SeekBar = view.findViewById(R.id.sb_count_of_female)
        var btnDone: Button = view.findViewById(R.id.btn_done)

        fun bind(deposition: Deposition){
            data.text = deposition.data
            cageFrom.text = deposition.numberOfCageFrom
            cageTo.text = deposition.numberOfCageTo

            //todo - обработчики
        }
    }
    class VaccinationViewHolder(view: View): RecyclerView.ViewHolder(view){
        var data: TextView = view.findViewById(R.id.data)
        var numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)

        var btnDone: Button = view.findViewById(R.id.btn_done)

        fun bind(vaccination: Vaccination){
            data.text = vaccination.data
            numberOfCage.text = vaccination.numberOfCage

            //todo - обработчики
        }
    }
    class InspectionViewHolder(view: View): RecyclerView.ViewHolder(view){
        var data: TextView = view.findViewById(R.id.data)
        var numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
        var weight: EditText = view.findViewById(R.id.txt_weight)

        var btnDone: Button = view.findViewById(R.id.btn_done)

        fun bind(inspection: Inspection){
            data.text = inspection.data
            numberOfCage.text = inspection.numberOfCage

            //todo - обработчики
        }
    }
    class ReproductionViewHolder(view: View): RecyclerView.ViewHolder(view){
        var data: TextView = view.findViewById(R.id.data)
        var takeFrom: TextView = view.findViewById(R.id.txt_from)
        var takeTo: TextView = view.findViewById(R.id.txt_to)

        var btnDone: Button = view.findViewById(R.id.btn_done)

        fun bind(reproduction: Reproduction){
            data.text = reproduction.data
            takeFrom.text = reproduction.takeFemaleFrom
            takeTo.text = reproduction.takeFemaleTo

            //todo - обработчики
        }
    }
    class KillViewHolder(view: View): RecyclerView.ViewHolder(view){
        var data: TextView = view.findViewById(R.id.data)
        var numberOfCage: TextView = view.findViewById(R.id.txt_number_of_cage)
        var weight: TextView = view.findViewById(R.id.txt_weight)

        var btnDone: Button = view.findViewById(R.id.btn_done)

        fun bind(kill: Kill){
            data.text = kill.data
            numberOfCage.text = kill.numberOfCage

            //todo - обработчики
        }
    }
}