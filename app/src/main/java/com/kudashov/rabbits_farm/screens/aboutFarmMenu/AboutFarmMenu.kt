package com.kudashov.rabbits_farm.screens.aboutFarmMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentAboutFarmMenuBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class AboutFarmMenu: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentAboutFarmMenuBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutFarmMenuBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        mBinding.btnExit.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarmMenu_to_aboutFarm)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}