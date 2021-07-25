package com.kudashov.rabbits_farm.screens.farm.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentFarmCageMenuBinding
import com.kudashov.rabbits_farm.screens.farm.FarmViewModel
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class FarmMenuCage : Fragment() {

    private val TAG: String = this::class.java.simpleName

    private var _binding: FragmentFarmCageMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FarmViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmCageMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        binding.btnExit.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_farmMenuCage_to_farm)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}