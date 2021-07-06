package com.kudashov.rabbits_farm.screens.aboutFarm

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
import com.kudashov.rabbits_farm.adapters.AboutFarmAdapter
import com.kudashov.rabbits_farm.databinding.FragmentAboutFarmBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class AboutFarm: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentAboutFarmBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: AboutFarmViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AboutFarmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutFarmBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        adapter = AboutFarmAdapter()
        recyclerView = mBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        mViewModel = ViewModelProvider(this).get(AboutFarmViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        mBinding.btnToMenu.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarm_to_aboutFarmMenu)
        }
        mBinding.btnRabbits.setOnClickListener{
            mViewModel.getRabbits()
        }
        mBinding.btnCages.setOnClickListener{
            mViewModel.getCages()
        }
        mViewModel.getRabbits()
    }

    private fun stateProcessing(state: StateAboutFarm){
        when (state){
            is StateAboutFarm.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
                setEnabled(true)
            }
            is StateAboutFarm.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                setEnabled(false)
                //todo - добавить лоадер
            }
            is StateAboutFarm.ListOfRabbitsReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
                setEnabled(true)
            }
            is StateAboutFarm.ListOfCageReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
                setEnabled(true)
            }
            is StateAboutFarm.Error<*> -> {
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                setEnabled(true)
            }
        }
    }

    private fun setEnabled(enable: Boolean){
        mBinding.apply {
            btnCages.isEnabled = enable
            btnKill.isEnabled = enable
            btnRabbits.isEnabled = enable
            btnSelectPair.isEnabled = enable
            btnToMenu.isEnabled = enable
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}