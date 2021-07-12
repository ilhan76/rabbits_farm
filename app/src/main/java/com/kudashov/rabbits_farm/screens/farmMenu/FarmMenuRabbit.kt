package com.kudashov.rabbits_farm.screens.farmMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.databinding.FragmentFarmRabbitMenuBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY

class FarmMenuRabbit : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmRabbitMenuBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: AboutFarmMenuViewModel

    private var isMale: Boolean = false
    private var isFemale: Boolean = false

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
            APP_ACTIVITY.navController.navigate(R.id.action_farmMenuRabbit_to_farm)
        }

        val list: List<String> = listOf("Фирма №1", "Фирма №2", "Фирма №3")
        val adapter = SpinnerAdapter(APP_ACTIVITY)
        adapter.setList(list)
        mBinding.spinnerNumberOfFarm.adapter = adapter

        mBinding.btnMale.setOnClickListener {
            mBinding.btnMale.setBackgroundResource(if (isMale) R.drawable.shape_menu else R.drawable.shape_btn_green)
            isMale = !isMale
        }
        mBinding.btnFemale.setOnClickListener {
            mBinding.btnFemale.setBackgroundResource(if (isFemale) R.drawable.shape_menu else R.drawable.shape_btn_green)
            isFemale = !isFemale
        }
    }

    private fun stateProcessing(state: StateAboutFarmMenu) {
        when (state) {
            is StateAboutFarmMenu.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
                //todo разблокировать кнопки
            }
            is StateAboutFarmMenu.Sending -> {
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