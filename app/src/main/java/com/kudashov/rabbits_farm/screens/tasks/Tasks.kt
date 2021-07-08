package com.kudashov.rabbits_farm.screens.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.AboutFarmAdapter
import com.kudashov.rabbits_farm.adapters.TasksAdapter
import com.kudashov.rabbits_farm.databinding.FragmentTasksBinding
import com.kudashov.rabbits_farm.screens.aboutFarm.AboutFarmViewModel
import com.kudashov.rabbits_farm.screens.aboutFarm.StateAboutFarm
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class Tasks : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentTasksBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: TasksViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        adapter = TasksAdapter()
        recyclerView = mBinding.tasksList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        mBinding.btnToMenu.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarm_to_aboutFarmMenu)
        }

        mViewModel.getTasks()
    }

    private fun stateProcessing(state: StateTasks){
        when (state){
            is StateTasks.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
            }
            is StateTasks.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo - добавить лоадер
            }
            is StateTasks.ListOfTasksReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
            }
            is StateTasks.Error<*> -> {
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}