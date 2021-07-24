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
import com.kudashov.rabbits_farm.adapters.TasksAdapter
import com.kudashov.rabbits_farm.databinding.FragmentTasksBinding
import com.kudashov.rabbits_farm.screens.dialogs.DeathDialog
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateTasks

class Tasks : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentTasksBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: TasksViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksAdapter

    private var isDone: Boolean = false

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
        //APP_ACTIVITY.moveUnderline(R.id.tasks)

        adapter = TasksAdapter()
        recyclerView = mBinding.tasksList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        mViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        initButtons()

        mViewModel.getTasks(isDone)
        mBinding.btnNotDone.setBackgroundResource(R.drawable.shape_btn_green)
    }

    private fun initButtons() {
        mBinding.btnDeath.setOnClickListener {
            val dialogDeath = DeathDialog()
            val transaction = parentFragmentManager.beginTransaction()
            dialogDeath.show(transaction, "Dialog_Death")
        }

        mBinding.btnDone.setOnClickListener {
            isDone = true
            mBinding.btnDone.setBackgroundResource(R.drawable.shape_btn_green)
            mBinding.btnNotDone.setBackgroundResource(R.drawable.shape_btn_grey)

            mViewModel.getTasks(isDone)
        }

        mBinding.btnNotDone.setOnClickListener {
            isDone = false
            mBinding.btnDone.setBackgroundResource(R.drawable.shape_btn_grey)
            mBinding.btnNotDone.setBackgroundResource(R.drawable.shape_btn_green)

            mViewModel.getTasks(isDone)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}