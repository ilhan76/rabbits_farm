package com.kudashov.rabbits_farm.screens.farmMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentFarmRabbitMenuBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class AboutFarmMenu: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmRabbitMenuBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: AboutFarmMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmRabbitMenuBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        mViewModel = ViewModelProvider(this).get(AboutFarmMenuViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        mBinding.btnExit.setOnClickListener {
            APP_ACTIVITY.mNavController.navigate(R.id.action_aboutFarmMenu_to_aboutFarm)
        }
    }

    private fun stateProcessing(state: StateAboutFarmMenu){
        when (state){
            is StateAboutFarmMenu.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
            is StateAboutFarmMenu.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo заблокировать кнопки
            }
            is StateAboutFarmMenu.ListReceivedSuccessfully<*> -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
            is StateAboutFarmMenu.Error<*> -> {
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