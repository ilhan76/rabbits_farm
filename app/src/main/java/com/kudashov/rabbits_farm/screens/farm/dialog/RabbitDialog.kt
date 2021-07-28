package com.kudashov.rabbits_farm.screens.farm.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.RabbitOperationsAdapter
import com.kudashov.rabbits_farm.databinding.DialogFragmentRabbitMoreInfoBinding
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.RH
import com.kudashov.rabbits_farm.utilits.StateRabbit

class RabbitDialog : DialogFragment() {

    private var _binding: DialogFragmentRabbitMoreInfoBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: RabbitViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RabbitOperationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentRabbitMoreInfoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(RH.drawable(R.color.transparent))

        mViewModel = ViewModelProvider(this).get(RabbitViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)


        adapter = RabbitOperationsAdapter()
        recyclerView = mBinding.historyOfOperationList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        initButtons()

        mViewModel.getOperations()
    }

    private fun initButtons() {
        mBinding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        mBinding.btnWeigh.setOnClickListener {
            val dialogWeigh =
                WeighDialog()
            val transaction = parentFragmentManager.beginTransaction()
            dialogWeigh.show(transaction, "Dialog_Weigh")
        }

        mBinding.btnChangeType.setOnClickListener {
            Toast.makeText(APP_ACTIVITY, "Меняем тип", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stateProcessing(state: StateRabbit) {
        when (state) {
            is StateRabbit.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
            }
            is StateRabbit.Sending -> {
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
            }
            is StateRabbit.Success -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
            }
            is StateRabbit.Error<*> -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

    }
}