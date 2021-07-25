package com.kudashov.rabbits_farm.screens.farm.filters

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.databinding.FragmentFarmRabbitMenuBinding
import com.kudashov.rabbits_farm.screens.farm.FarmViewModel
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.statuses.cage.StatusOfCage
import com.kudashov.rabbits_farm.utilits.statuses.rabbit.TypeOfRabbit

class FarmMenuRabbit : Fragment(), AdapterView.OnItemSelectedListener {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmRabbitMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FarmViewModel

    private lateinit var adapterNumberOfFarm: SpinnerAdapter
    private lateinit var adapterBreed: SpinnerAdapter
    private lateinit var adapterStatus: SpinnerAdapter

    private var isMale: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmRabbitMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        val listNumberOfFarm: List<String> = listOf("Ферма №1", "Ферма №2", "Ферма №3", "Ферма №4")

        adapterNumberOfFarm = SpinnerAdapter(requireContext())
        adapterNumberOfFarm.setList(listNumberOfFarm)
        binding.spinnerNumberOfFarm.adapter = adapterNumberOfFarm

        // todo - взять породы из SharedPreferences
        val listBreed = listOf("Паннон", "Еще порода", "Какая-то порода")
        adapterBreed = SpinnerAdapter(requireContext())
        adapterBreed.setList(listBreed)
        binding.spinnerBreed.adapter = adapterBreed

        val listStatus = listOf(StatusOfCage.statuses[StatusOfCage.needClean]!!, StatusOfCage.statuses[StatusOfCage.needRepair]!!)
        adapterStatus = SpinnerAdapter(requireContext())
        adapterStatus.setList(listStatus)
        binding.spinnerStatus.adapter = adapterStatus

        initButton()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            btnExit.setOnClickListener {
                RabbitFilter.throwOff()
                APP_ACTIVITY.navController.popBackStack()
            }

            btnShow.setOnClickListener {
                RabbitFilter.ageFrom = editTxtAgeFrom.text.toString().toInt()
                RabbitFilter.ageTo = editTxtAgeTo.text.toString().toInt()

                RabbitFilter.weightFrom = editTxtNumberOfCageFrom.toString().toDouble()
                RabbitFilter.weightTo = editTxtNumberOfCageTo.toString().toDouble()

                APP_ACTIVITY.navController.popBackStack()
            }

            //binding.spinnerNumberOfFarm.onItemSelectedListener = this

            cbFattening.setOnClickListener {
                if (binding.cbFattening.isChecked) {
                    RabbitFilter.type.add(TypeOfRabbit.fattening)
                } else {
                    RabbitFilter.type.remove(TypeOfRabbit.fattening)
                }
            }
            cbBirth.setOnClickListener {
                if (binding.cbBirth.isChecked) {
                    RabbitFilter.type.add(TypeOfRabbit.mather)
                    RabbitFilter.type.add(TypeOfRabbit.father)
                } else {
                    RabbitFilter.type.remove(TypeOfRabbit.mather)
                    RabbitFilter.type.remove(TypeOfRabbit.father)
                }
            }
            cbBaby.setOnClickListener {
                if (binding.cbBaby.isChecked) {
                    RabbitFilter.type.add(TypeOfRabbit.baby)
                } else {
                    RabbitFilter.type.remove(TypeOfRabbit.baby)
                }
            }

            btnMale.setOnClickListener {
                isMale = true
                binding.btnMale.setBackgroundResource(if (!isMale) R.drawable.shape_menu else R.drawable.shape_btn_green)
                binding.btnFemale.setBackgroundResource(if (isMale) R.drawable.shape_menu else R.drawable.shape_btn_green)
                RabbitFilter.isMale = 1
            }
            btnFemale.setOnClickListener {
                isMale = false
                binding.btnMale.setBackgroundResource(if (!isMale) R.drawable.shape_menu else R.drawable.shape_btn_green)
                binding.btnFemale.setBackgroundResource(if (isMale) R.drawable.shape_menu else R.drawable.shape_btn_green)
                RabbitFilter.isMale = 0
            }
        }
    }

    private fun initButton() {
        binding.apply {
            if (RabbitFilter.isMale == 1){
                btnMale.setBackgroundResource(R.drawable.shape_btn_green)
                btnFemale.setBackgroundResource(R.drawable.shape_menu)
            } else if (RabbitFilter.isMale == 0){
                btnMale.setBackgroundResource(R.drawable.shape_menu)
                btnFemale.setBackgroundResource(R.drawable.shape_btn_green)
            }

            if (RabbitFilter.type.contains(TypeOfRabbit.baby))
                cbBaby.isChecked = true
            if (RabbitFilter.type.contains(TypeOfRabbit.father) || RabbitFilter.type.contains(TypeOfRabbit.mather))
                cbBirth.isChecked = true
            if (RabbitFilter.type.contains(TypeOfRabbit.fattening))
                cbFattening.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
