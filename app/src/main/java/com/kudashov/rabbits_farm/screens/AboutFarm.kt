package com.kudashov.rabbits_farm.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentAboutFarmBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class AboutFarm: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentAboutFarmBinding? = null
    private val mBinding get() = _binding!!

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
        mBinding.btnToMenu.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarm_to_aboutFarmMenu)
        }
        mBinding.btnRabbits.setOnClickListener{
            //mViewModel.getRabbits()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}