package com.kudashov.rabbits_farm.screens.birth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.BirthAdapter
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.adapters.delegates.BirthDelegate
import com.kudashov.rabbits_farm.databinding.FragmentBirthBinding
import com.kudashov.rabbits_farm.screens.birth.dialog.TakeBirthDialog
import com.kudashov.rabbits_farm.screens.farm.filters.cage.CageFilter
import com.kudashov.rabbits_farm.screens.farm.filters.rabbit.RabbitFilter
import com.kudashov.rabbits_farm.utilits.PaginationScrollListener
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateBirth
import com.kudashov.rabbits_farm.utilits.const.sort.*

class Birth : Fragment(), BirthDelegate {

    companion object {
        const val ARG_ID = "birth_id"
        const val ARG_VIEW_MODEL = "view_model"
    }

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentBirthBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BirthViewModel

    private lateinit var adapter: BirthAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerAdapter: SpinnerAdapter

    private var isConfirmed: Boolean = false

    private val typesOfSort = listOf(
        "",
        TYPES_SORT_BIRTH[SORT_BIRTH_CAGE_NUMBER],
        TYPES_SORT_BIRTH[SORT_BIRTH_CAGE_NUMBER_INV],
        TYPES_SORT_BIRTH[SORT_BIRTH_TIME],
        TYPES_SORT_BIRTH[SORT_BIRTH_TIME_INV]
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.birth)

        adapter = BirthAdapter()
        adapter.attachDelegate(this)
        recyclerView = binding.rvBirth
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
/*        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadNextPage() {
                Log.d(TAG, "loadMoreItems: NEXT PAGE")
                viewModel.nextPage()

                if (isConfirmed)
                    viewModel.getBirth(isConfirmed, null)
                else
                    viewModel.getBirth(isConfirmed, null)
            }
        })*/

        viewModel = ViewModelProvider(this).get(BirthViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        spinnerAdapter = SpinnerAdapter(requireContext())
        spinnerAdapter.setList(typesOfSort)
        binding.spinner.adapter = spinnerAdapter

        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            btnConfirmed.setOnClickListener {
                isConfirmed = true
                loadData()
            }

            btnNotYetConfirmed.setOnClickListener {
                isConfirmed = false
                loadData()
            }

            spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d(TAG, "onItemSelected: SPINNER")
                        var type: String? = null
                        viewModel.cleanPage()
                        for ((k, v) in TYPES_SORT_RABBIT)
                            if (typesOfSort[position] == v) type = k
                        viewModel.setOrderBy(type)
                    }
                }
        }
    }

    private fun loadData() {
        viewModel.cleanPage()

        if (isConfirmed) {
            binding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)
            binding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)

            viewModel.getBirth(isConfirmed)
        } else {
            binding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)
            binding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)

            viewModel.getBirth(isConfirmed)
        }
    }

    private fun stateProcessing(state: StateBirth) {
        when (state) {
            is StateBirth.Default -> {
                Log.d(TAG, "stateProcessing: Birth Default")
                loadData()
                APP_ACTIVITY.hideLoader()
            }
            is StateBirth.Sending -> {
                Log.d(TAG, "stateProcessing: Birth Sending")
                APP_ACTIVITY.showLoader()
                binding.txtNoItem.visibility = View.GONE
            }
            is StateBirth.SuccessBirth -> {
                Log.d(TAG, "stateProcessing: Birth Success")
                adapter.setList(state.list)
                APP_ACTIVITY.hideLoader()
                binding.txtNoItem.visibility = View.GONE
            }
            is StateBirth.Error<*> -> {
                Log.d(TAG, "stateProcessing: Birth Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
                binding.txtNoItem.visibility = View.GONE
            }
            StateBirth.NoItem -> {
                Log.d(TAG, "stateProcessing: No Item")
                APP_ACTIVITY.hideLoader()
                adapter.setList(emptyList())
                binding.txtNoItem.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun confirmPregnancy(id: Int, isConfirmed: Boolean) {
        viewModel.confirmPregnancy(id, isConfirmed)
    }

    override fun takeBirth(id: Int) {
        val bundle = Bundle()
        bundle.putInt(ARG_ID, id)
        bundle.putSerializable(ARG_VIEW_MODEL, viewModel)
        val dialogBirth = TakeBirthDialog.newInstance(bundle)
        val transaction = parentFragmentManager.beginTransaction()
        dialogBirth.show(transaction, "Dialog_TakeBirth")
    }
}