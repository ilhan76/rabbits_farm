package com.kudashov.rabbits_farm.screens.farm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.FarmAdapter
import com.kudashov.rabbits_farm.adapters.FarmDelegate
import com.kudashov.rabbits_farm.data.Rabbit
import com.kudashov.rabbits_farm.databinding.FragmentFarmBinding
import com.kudashov.rabbits_farm.screens.dialogs.RabbitDialog
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class Farm: Fragment(), FarmDelegate {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: FarmViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FarmAdapter

    private var isRabbit: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.farm)

        adapter = FarmAdapter()
        recyclerView = mBinding.farmList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.attachDelegate(this)

        mViewModel = ViewModelProvider(this).get(FarmViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        initButtons()

        mViewModel.getRabbits()
    }

    private fun initButtons() {
        mBinding.btnToMenu.setOnClickListener {
            if (isRabbit)
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuRabbit)
            else
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuCage)
        }

        mBinding.btnRabbits.setOnClickListener{
            isRabbit = true
            mBinding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_green)
            mBinding.btnCages.setBackgroundResource(R.drawable.shape_btn_grey)

            mViewModel.getRabbits()
        }
        mBinding.btnCages.setOnClickListener{
            isRabbit = false
            mBinding.btnCages.setBackgroundResource(R.drawable.shape_btn_green)
            mBinding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_grey)

            mViewModel.getCages()
        }
    }

    private fun stateProcessing(state: StateAboutFarm){
        when (state){
            is StateAboutFarm.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
            }
            is StateAboutFarm.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo - добавить лоадер
            }
            is StateAboutFarm.ListOfRabbitsReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
                mBinding.buttonsPanel.visibility = View.GONE
            }
            is StateAboutFarm.ListOfCageReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                mBinding.buttonsPanel.visibility = View.VISIBLE
                adapter.setList(state.list)
            }
            is StateAboutFarm.Error<*> -> {
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openMoreRabbitInfo(rabbit: Rabbit) {
        Toast.makeText(context, "Нажали на элемент списка", Toast.LENGTH_SHORT).show()

        val rabbitDialog = RabbitDialog()
        val transaction = parentFragmentManager.beginTransaction()
        rabbitDialog.show(transaction,"Rabbit")
    }
}