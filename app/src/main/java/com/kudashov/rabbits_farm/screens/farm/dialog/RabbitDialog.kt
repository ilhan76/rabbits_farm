package com.kudashov.rabbits_farm.screens.farm.dialog

import android.os.Bundle
import android.util.Log
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
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.mapper.RabbitMapper
import com.kudashov.rabbits_farm.databinding.DialogFragmentRabbitMoreInfoBinding
import com.kudashov.rabbits_farm.screens.farm.Farm
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.RH
import com.kudashov.rabbits_farm.utilits.StateRabbit
import com.kudashov.rabbits_farm.utilits.statuses.rabbit.TypeOfRabbit

class RabbitDialog : DialogFragment() {

    companion object {
        const val ARG_RABBIT: String = "rabbit"

        fun newInstance(bundle: Bundle): RabbitDialog {
            val dialog = RabbitDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    private val TAG: String? = this::class.java.simpleName

    private var _binding: DialogFragmentRabbitMoreInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RabbitViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RabbitOperationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: Dialog is created")
        _binding = DialogFragmentRabbitMoreInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: Dialog is destroyed")
        _binding = null
    }

    private fun init() {
        Log.d(TAG, "init: Init dialog")
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(RH.drawable(R.color.transparent))

        viewModel = ViewModelProvider(this).get(RabbitViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        adapter = RabbitOperationsAdapter()
        recyclerView = binding.historyOfOperationList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        initButtons()

        viewModel.getRabbitMoreInf(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
        viewModel.getOperations(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
    }

    private fun initButtons() {
        binding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.btnWeigh.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ARG_RABBIT, arguments?.get(ARG_RABBIT) as RabbitMoreInfDto)

            val dialogWeigh = WeighDialog.newInstance(bundle)
            val transaction = parentFragmentManager.beginTransaction()
            dialogWeigh.show(transaction, "Dialog_Weigh")
        }

        binding.btnChangeType.setOnClickListener {
            Toast.makeText(APP_ACTIVITY, "Меняем тип", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(rabbit: RabbitMoreInfDto) {
        binding.apply {
            txtRabbitId.text = resources.getString(
                R.string.dialog_rabbit_txt_id,
                rabbit.id
            )

            txtWeight.text = resources.getString(
                R.string.dialog_rabbit_txt_weight,
                rabbit.weight.toString()
            )

            txtBirthday.text = resources.getString(
                R.string.dialog_rabbit_txt_birthday,
                RabbitMapper.getBirthday(rabbit.birthday)
            )

            txtAge.text = resources.getString(
                R.string.dialog_rabbit_txt_age,
                RabbitMapper.getAge(rabbit.birthday)
            )
            //todo - склонение слова "дней"

            txtType.text = resources.getString(
                R.string.dialog_rabbit_txt_type,
                RabbitMapper.getType(rabbit.current_type)
            )
            txtBreed.text = resources.getString(
                R.string.dialog_rabbit_txt_breed,
                rabbit.breed
            )
            txtNumberOfCage.text = resources.getString(
                R.string.dialog_rabbit_txt_number_of_cage,
                rabbit.cage.farm_number,
                rabbit.cage.number,
                rabbit.cage.letter
            )
            txtStatus.text = resources.getString(
                R.string.dialog_rabbit_txt_status,
                rabbit.status
            )
            //todo - мапинг и правильное отображение статусов
            if (rabbit.current_type == TypeOfRabbit.mather || rabbit.current_type == TypeOfRabbit.father) {
                txtCountOfPins.text = resources.getString(
                    R.string.dialog_rabbit_txt_output,
                    rabbit.output.toString()
                )
                txtAveragePins.text = resources.getString(
                    R.string.dialog_rabbit_txt_output_efficiency,
                    rabbit.output_efficiency.toString()
                )
            }

        }
    }

    private fun stateProcessing(state: StateRabbit) {
        when (state) {
            is StateRabbit.Default -> {
                Toast.makeText(context, "Rabbit Default", Toast.LENGTH_SHORT).show()
            }
            is StateRabbit.Sending -> {
                Toast.makeText(context, "Rabbit Sending", Toast.LENGTH_SHORT).show()
            }
            is StateRabbit.SuccessRabbit -> {
                Toast.makeText(context, "Rabbit Success", Toast.LENGTH_SHORT).show()
                arguments?.putSerializable(ARG_RABBIT, state.rabbit)
                loadData(state.rabbit)
                //adapter.setList(state.)
            }
            is StateRabbit.SuccessOperations -> {
                Toast.makeText(context, "Rabbit Success Operations", Toast.LENGTH_SHORT).show()
                adapter.setList(state.operations)
            }
            is StateRabbit.Error<*> -> {
                Toast.makeText(context, "Rabbit Error", Toast.LENGTH_SHORT).show()
            }
        }

    }
}