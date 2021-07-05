package com.kudashov.rabbits_farm.screens.aboutFarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentAboutFarmBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class AboutFarm: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentAboutFarmBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: AboutFarmViewModel

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

        mViewModel = ViewModelProvider(this).get(AboutFarmViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        mBinding.btnToMenu.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarm_to_aboutFarmMenu)
        }
        mBinding.btnRabbits.setOnClickListener{
            mViewModel.getRabbits()
        }
    }

    private fun stateProcessing(state: StateAboutFarm){
        when (state){
            is StateAboutFarm.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
            is StateAboutFarm.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo заблокировать кнопки / добавить лоадер
            }
            is StateAboutFarm.ListReceivedSuccessfully<*> -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
            is StateAboutFarm.Error<*> -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}