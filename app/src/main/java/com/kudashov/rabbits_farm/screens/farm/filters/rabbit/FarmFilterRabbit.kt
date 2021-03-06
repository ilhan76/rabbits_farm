package com.kudashov.rabbits_farm.screens.farm.filters.rabbit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.data.domain.BreedDomain
import com.kudashov.rabbits_farm.data.dto.BreedDto
import com.kudashov.rabbits_farm.databinding.FragmentFarmRabbitFilterBinding
import com.kudashov.rabbits_farm.screens.farm.Farm
import com.kudashov.rabbits_farm.screens.farm.FarmViewModel
import com.kudashov.rabbits_farm.screens.farm.filters.FilterViewModel
import com.kudashov.rabbits_farm.utilits.BaseState
import com.kudashov.rabbits_farm.utilits.const.*
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_CLEAN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_REPAIR
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_BABY
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_FATHER
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_FATTENING
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_MATHER

class FarmFilterRabbit : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmRabbitFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FilterViewModel

    private lateinit var adapterNumberOfFarm: SpinnerAdapter
    private lateinit var adapterBreed: SpinnerAdapter
    private lateinit var adapterStatus: SpinnerAdapter

    private var isMale: Boolean = false
    private lateinit var listNumberOfFarm: List<String>
    private lateinit var listBreed: List<BreedDomain>
    private lateinit var listStatus: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFarmRabbitFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(FilterViewModel::class.java)
        viewModel.statuses.observe(viewLifecycleOwner, Observer {
            listStatus = it
            adapterStatus = SpinnerAdapter(requireContext())
            adapterStatus.setList(listStatus)
            binding.spinnerStatus.adapter = adapterStatus
        })
        viewModel.getListOfStatuses()

        viewModel.stateBreed.observe(viewLifecycleOwner, this::stateBreedProcessing)

        listNumberOfFarm = listOf("",
            farm1,
            farm2,
            farm3,
            farm4
        )
        adapterNumberOfFarm = SpinnerAdapter(requireContext())
        adapterNumberOfFarm.setList(listNumberOfFarm)
        binding.spinnerNumberOfFarm.adapter = adapterNumberOfFarm

        initListeners()
        loadData()
    }

    private fun initListeners() {
        binding.apply {
            btnExit.setOnClickListener {
                RabbitFilter.throwOff()
                val bundle = Bundle()
                bundle.putBoolean(Farm.ARG_IS_RABBIT, true)
                APP_ACTIVITY.navController.navigate(R.id.action_farmMenuRabbit_to_farm, bundle)
            }

            btnShow.setOnClickListener {
                // todo - ?????????????????? ???????????????? ??????????
                if (editTxtAgeFrom.text.isNotEmpty())
                    RabbitFilter.ageFrom = editTxtAgeFrom.text.toString().toInt()
                else RabbitFilter.ageFrom = null
                if (editTxtAgeTo.text.isNotEmpty())
                    RabbitFilter.ageTo = editTxtAgeTo.text.toString().toInt()
                else RabbitFilter.ageTo = null
                if (editTxtNumberOfCageFrom.text.isNotEmpty())
                    RabbitFilter.cageNumberFrom = editTxtNumberOfCageFrom.text.toString().toInt()
                else RabbitFilter.cageNumberFrom = null
                if (editTxtNumberOfCageTo.text.isNotEmpty())
                    RabbitFilter.cageNumberTo = editTxtNumberOfCageTo.text.toString().toInt()
                else RabbitFilter.cageNumberTo = null

                Log.d(TAG, "initListeners: $RabbitFilter")
                val bundle = Bundle()
                bundle.putBoolean(Farm.ARG_IS_RABBIT, true)
                APP_ACTIVITY.navController.navigate(R.id.action_farmMenuRabbit_to_farm, bundle)
            }

            spinnerNumberOfFarm.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        RabbitFilter.farmNumber = position
                    } else RabbitFilter.farmNumber = null
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
            spinnerBreed.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0)
                        RabbitFilter.breed = listBreed[position].id
                    else RabbitFilter.breed = null
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
            spinnerStatus.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> RabbitFilter.status =
                            CAGE_STATUS_NEED_CLEAN
                        2 -> RabbitFilter.status =
                            CAGE_STATUS_NEED_REPAIR
                        else -> RabbitFilter.status = null
                    }
                    Log.d(TAG, "onItemSelected: ${RabbitFilter.status}")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            cbFattening.setOnClickListener {
                if (binding.cbFattening.isChecked) {
                    RabbitFilter.type.add(RABBIT_TYPE_FATTENING)
                } else {
                    RabbitFilter.type.remove(RABBIT_TYPE_FATTENING)
                }
            }
            cbBirth.setOnClickListener {
                if (binding.cbBirth.isChecked) {
                    Toast.makeText(context, "checked", Toast.LENGTH_SHORT).show()
                    RabbitFilter.type.add(RABBIT_TYPE_MATHER)
                    RabbitFilter.type.add(RABBIT_TYPE_FATHER)
                } else if (!binding.cbBirth.isChecked) {
                    Toast.makeText(context, "unchecked", Toast.LENGTH_SHORT).show()
                    RabbitFilter.type.remove(RABBIT_TYPE_MATHER)
                    RabbitFilter.type.remove(RABBIT_TYPE_FATHER)
                }
            }
            cbBaby.setOnClickListener {
                if (binding.cbBaby.isChecked) {
                    Toast.makeText(context, "checked", Toast.LENGTH_SHORT).show()
                    RabbitFilter.type.add(RABBIT_TYPE_BABY)
                } else if (!binding.cbBaby.isChecked) {
                    Toast.makeText(context, "unchecked", Toast.LENGTH_SHORT).show()
                    RabbitFilter.type.remove(RABBIT_TYPE_BABY)
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

    private fun loadData() {
        binding.apply {
            if (RabbitFilter.isMale == 1) {
                btnMale.setBackgroundResource(R.drawable.shape_btn_green)
                btnFemale.setBackgroundResource(R.drawable.shape_menu)
            } else if (RabbitFilter.isMale == 0) {
                btnMale.setBackgroundResource(R.drawable.shape_menu)
                btnFemale.setBackgroundResource(R.drawable.shape_btn_green)
            }

            if (RabbitFilter.type.contains(RABBIT_TYPE_BABY))
                cbBaby.isChecked = true
            if (RabbitFilter.type.contains(RABBIT_TYPE_FATHER) ||
                RabbitFilter.type.contains(RABBIT_TYPE_MATHER)
            )
                cbBirth.isChecked = true
            if (RabbitFilter.type.contains(RABBIT_TYPE_FATTENING))
                cbFattening.isChecked = true

            spinnerNumberOfFarm.setSelection(RabbitFilter.farmNumber ?: 0)
            spinnerBreed.setSelection(RabbitFilter.breed ?: 0)

            if (RabbitFilter.ageFrom != null) editTxtAgeFrom.setText(
                RabbitFilter.ageFrom.toString(),
                TextView.BufferType.EDITABLE
            )
            if (RabbitFilter.ageTo != null) editTxtAgeTo.setText(
                RabbitFilter.ageTo.toString(),
                TextView.BufferType.EDITABLE
            )
            if (RabbitFilter.cageNumberFrom != null) editTxtNumberOfCageFrom.setText(
                RabbitFilter.cageNumberFrom.toString(),
                TextView.BufferType.EDITABLE
            )
            if (RabbitFilter.cageNumberTo != null) editTxtNumberOfCageTo.setText(
                RabbitFilter.cageNumberTo.toString(),
                TextView.BufferType.EDITABLE
            )

            when (RabbitFilter.status) {
                CAGE_STATUS_NEED_CLEAN -> spinnerStatus.setSelection(1)
                CAGE_STATUS_NEED_REPAIR -> spinnerStatus.setSelection(2)
            }
        }
    }

    private fun stateBreedProcessing(state: BaseState){
        when(state){
            BaseState.Default -> {
                Log.d(TAG, "stateBreedProcessing: Default")
                viewModel.getBreed()
            }
            BaseState.Sending -> {
                Log.d(TAG, "stateBreedProcessing: Loading")
            }
            is BaseState.Error<*> -> {
                Log.d(TAG, "stateBreedProcessing: ${state.error.toString()}")
            }
            is BaseState.Success<*> -> {
                Log.d(TAG, "stateBreedProcessing: Success")
                listBreed = state.content as List<BreedDomain>
                adapterBreed = SpinnerAdapter(requireContext())
                adapterBreed.setList(listBreed)
                binding.spinnerBreed.adapter = adapterBreed
                binding.spinnerBreed.setSelection((RabbitFilter.breed?.minus(1)) ?: 0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}