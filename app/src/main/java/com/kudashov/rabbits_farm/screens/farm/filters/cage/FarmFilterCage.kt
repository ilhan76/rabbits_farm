package com.kudashov.rabbits_farm.screens.farm.filters.cage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.databinding.FragmentFarmCageFilterBinding
import com.kudashov.rabbits_farm.screens.farm.Farm
import com.kudashov.rabbits_farm.screens.farm.FarmViewModel
import com.kudashov.rabbits_farm.utilits.const.*
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.*

class FarmFilterCage : Fragment() {

    private val TAG: String = this::class.java.simpleName

    private var _binding: FragmentFarmCageFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FarmViewModel

    private lateinit var adapterNumberOfFarm: SpinnerAdapter
    private lateinit var adapterTypeOfCage: SpinnerAdapter

    private lateinit var listNumberOfFarm: List<String>
    private lateinit var listTypes: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmCageFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel = arguments?.get(Farm.ARG_VIEW_MODEL) as FarmViewModel

        listNumberOfFarm = listOf(
            "",
            farm1,
            farm2,
            farm3,
            farm4
        )
        adapterNumberOfFarm = SpinnerAdapter(requireContext())
        adapterNumberOfFarm.setList(listNumberOfFarm)
        binding.spinnerFarmNumber.adapter = adapterNumberOfFarm

        listTypes = viewModel.getListOfTypes()

        adapterTypeOfCage = SpinnerAdapter(requireContext())
        adapterTypeOfCage.setList(listTypes)
        binding.spinnerTypeOfCage.adapter = adapterTypeOfCage

        initListeners()
        loadData()
    }

    private fun initListeners() {
        binding.apply {
            btnExit.setOnClickListener {
                CageFilter.throwOff()
                val bundle = Bundle()
                bundle.putBoolean(Farm.ARG_IS_RABBIT, false)
                APP_ACTIVITY.navController.navigate(R.id.action_farmMenuCage_to_farm, bundle)
            }

            btnShow.setOnClickListener {
                //todo - валидация
                if (editTxtCountOfRabbitFrom.text.isNotEmpty())
                    CageFilter.countOfRabbitFrom = editTxtCountOfRabbitFrom.text.toString().toInt()
                else
                    CageFilter.countOfRabbitFrom = null

                if (editTxtCountOfRabbitTo.text.isNotEmpty())
                    CageFilter.countOfRabbitTo = editTxtCountOfRabbitTo.text.toString().toInt()
                else
                    CageFilter.countOfRabbitTo = null

                val bundle = Bundle()
                bundle.putBoolean(Farm.ARG_IS_RABBIT, false)
                Log.d(TAG, "initListeners: $CageFilter")
                Toast.makeText(context, "false", Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.navController.navigate(R.id.action_farmMenuCage_to_farm, bundle)
            }

            spinnerFarmNumber.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position != 0)
                            CageFilter.farmNumber = position
                        else
                            CageFilter.farmNumber = null
                    }

                }
            spinnerTypeOfCage.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when (position) {
                            1 -> CageFilter.type = CAGE_TYPE_FATTENING
                            2 -> {
                                CageFilter.type = CAGE_TYPE_MOTHER
                                CageFilter.isParallel = 0
                            }
                            3 -> {
                                CageFilter.type = CAGE_TYPE_MOTHER_PARALLEL
                                CageFilter.isParallel = 1
                            }
                            else -> CageFilter.type = null
                        }
                    }
                }

            cbNeedClear.setOnClickListener {
                if (cbNeedClear.isChecked) {
                    CageFilter.status.add(CAGE_STATUS_NEED_CLEAN)
                } else {
                    CageFilter.status.remove(CAGE_STATUS_NEED_CLEAN)
                }
            }
            cbNeedRepair.setOnClickListener {
                if (cbNeedRepair.isChecked)
                    CageFilter.status.add(CAGE_STATUS_NEED_REPAIR)
                else
                    CageFilter.status.remove(CAGE_STATUS_NEED_REPAIR)
            }
        }
    }

    private fun loadData() {
        Log.d(TAG, "loadData: CageFilter $CageFilter")
        binding.apply {
            if (CageFilter.farmNumber != null)
                spinnerFarmNumber.setSelection(CageFilter.farmNumber!!)
            else spinnerFarmNumber.setSelection(0)

            when (CageFilter.type) {
                CAGE_TYPE_FATTENING -> spinnerTypeOfCage.setSelection(1)
                CAGE_TYPE_MOTHER -> {
                    if (CageFilter.isParallel != null && CageFilter.isParallel == 0)
                        spinnerTypeOfCage.setSelection(2)
                    else spinnerTypeOfCage.setSelection(3)
                }
                else -> spinnerTypeOfCage.setSelection(0)
            }

            if (CageFilter.countOfRabbitFrom != null)
                editTxtCountOfRabbitFrom.setText(CageFilter.countOfRabbitFrom.toString())
            if (CageFilter.countOfRabbitTo != null)
                editTxtCountOfRabbitTo.setText(CageFilter.countOfRabbitTo.toString())

            if (CageFilter.status.contains(CAGE_STATUS_NEED_CLEAN))
                cbNeedClear.isChecked = true
            if (CageFilter.status.contains(CAGE_STATUS_NEED_REPAIR))
                cbNeedRepair.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}