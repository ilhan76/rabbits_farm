package com.kudashov.rabbits_farm.screens.farm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.FarmAdapter
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.adapters.delegates.FarmDelegate
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.databinding.FragmentFarmBinding
import com.kudashov.rabbits_farm.screens.farm.dialog.RabbitDialog
import com.kudashov.rabbits_farm.screens.farm.filters.cage.CageFilter
import com.kudashov.rabbits_farm.screens.farm.filters.rabbit.RabbitFilter
import com.kudashov.rabbits_farm.utilits.PaginationScrollListener
import com.kudashov.rabbits_farm.utilits.StateAboutFarm
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.const.sort.*


class Farm : Fragment(), FarmDelegate {

    companion object {
        const val ARG_RABBIT_ID = "rabbit_id"
        const val ARG_VIEW_MODEL = "view_model"
        const val ARG_IS_RABBIT = "is_rabbit"
    }

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FarmViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var rvAdapter: FarmAdapter
    private lateinit var spinnerAdapter: SpinnerAdapter

    private var isRabbit: Boolean = true
    private lateinit var typesOfSort: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: Destroy")
        _binding = null
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.farm)

        viewModel = ViewModelProvider(this).get(FarmViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        rvAdapter = FarmAdapter()
        rvAdapter.attachDelegate(this)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView = binding.farmList
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rvAdapter
        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadNextPage() {
                Log.d(TAG, "loadMoreItems: NEXT PAGE")
                viewModel.nextPage()

                if (isRabbit)
                    viewModel.getRabbits()
                else
                    viewModel.getCages()
            }
        })

        isRabbit = try {
            arguments?.get(ARG_IS_RABBIT) as Boolean
        } catch (e: Exception) {
            true
        }
        loadData()
        initListeners()
    }

    private fun initListeners() {
        binding.btnToMenu.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ARG_VIEW_MODEL, viewModel)
            if (isRabbit) {
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuRabbit, bundle)
            } else {
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuCage, bundle)
            }
        }

        binding.btnRabbits.setOnClickListener {
            isRabbit = true
            loadData()
        }
        binding.btnCages.setOnClickListener {
            isRabbit = false
            loadData()
        }

        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "onItemSelected: SPINNER")
                    if (isRabbit) {
                        viewModel.cleanPage()
                        RabbitFilter.orderBy = typesOfSort[position]
                        viewModel.getRabbits()
                    } else {
                        viewModel.cleanPage()
                        CageFilter.orderBy = typesOfSort[position]
                        viewModel.getCages()
                    }
                }
            }
    }

    private fun loadData() {
        viewModel.cleanPage()

        if (isRabbit) {
            binding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_green)
            binding.btnCages.setBackgroundResource(R.drawable.shape_btn_grey)

            typesOfSort = listOf(
                "",
                SORT_RABBIT_AGE,
                SORT_RABBIT_AGE_INV,
                SORT_RABBIT_SEX,
                SORT_RABBIT_FARM_NUMBER,
                SORT_RABBIT_CAGE_NUMBER,
                SORT_RABBIT_TYPE,
                SORT_RABBIT_BREED,
                SORT_RABBIT_STATUS,
                SORT_RABBIT_WEIGHT,
                SORT_RABBIT_WEIGHT_INV
            )

            binding.spinner.setSelection(typesOfSort.indexOf(RabbitFilter.orderBy))

            binding.buttonsPanel.visibility = View.GONE

            binding.txtListTitle1.text =
                resources.getString(R.string.farm_rabbits_txt_number_of_cage)
            binding.txtListTitle2.text = resources.getString(R.string.farm_rabbits_txt_age)
            binding.txtListTitle3.text = resources.getString(R.string.farm_rabbits_txt_gender)
            binding.txtListTitle4.text = resources.getString(R.string.farm_rabbits_txt_type)
        } else {
            binding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_grey)
            binding.btnCages.setBackgroundResource(R.drawable.shape_btn_green)

            typesOfSort = listOf(
                "",
                SORT_CAGE_FARM_NUMBER,
                SORT_CAGE_NUMBER,
                SORT_CAGE_NUMBER_RABBITS,
                SORT_CAGE_STATUS,
                SORT_CAGE_FARM_NUMBER_INV,
                SORT_CAGE_NUMBER_INV,
                SORT_CAGE_NUMBER_RABBITS_INV,
                SORT_CAGE_STATUS_INV
            )

            binding.spinner.setSelection(typesOfSort.indexOf(CageFilter.orderBy))

            binding.buttonsPanel.visibility = View.VISIBLE

            binding.txtListTitle1.text = resources.getString(R.string.farm_cages_txt_number_of_farm)
            binding.txtListTitle2.text = resources.getString(R.string.farm_cages_txt_number)
            binding.txtListTitle3.text = resources.getString(R.string.farm_cages_txt_type)
            binding.txtListTitle4.text = resources.getString(R.string.farm_cages_txt_status)
        }

        spinnerAdapter = SpinnerAdapter(requireContext())
        spinnerAdapter.setList(typesOfSort)
        binding.spinner.adapter = spinnerAdapter
    }

    private fun stateProcessing(state: StateAboutFarm) {
        when (state) {
            is StateAboutFarm.Default -> {
                Log.d(TAG, "stateProcessing: Farm Default")
                APP_ACTIVITY.hideLoader()
            }
            is StateAboutFarm.Sending -> {
                Log.d(TAG, "stateProcessing: Farm Sending")
                APP_ACTIVITY.showLoader()
            }
            is StateAboutFarm.ListOfRabbitsReceived -> {
                Log.d(TAG, "stateProcessing: Farm Success (List Of Rabbits Received)")
                APP_ACTIVITY.hideLoader()
                rvAdapter.setList(state.list)
            }
            is StateAboutFarm.ListOfCageReceived -> {
                Log.d(TAG, "stateProcessing: Farm Success (List Of Cage Received)")
                APP_ACTIVITY.hideLoader()
                rvAdapter.setList(state.list)
            }
            is StateAboutFarm.Error<*> -> {
                Log.d(TAG, "stateProcessing: Farm Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
        }
    }

    override fun openMoreRabbitInfo(rabbit: Rabbit) {
        Toast.makeText(context, "Нажали на элемент списка", Toast.LENGTH_SHORT).show()

        val bundle = Bundle()
        bundle.putInt(ARG_RABBIT_ID, rabbit.id)
        val rabbitDialog = RabbitDialog.newInstance(bundle)
        val transaction = parentFragmentManager.beginTransaction()
        rabbitDialog.show(transaction, "Rabbit")

        Log.d(TAG, "openMoreRabbitInfo: Dialog is opened")
    }
}