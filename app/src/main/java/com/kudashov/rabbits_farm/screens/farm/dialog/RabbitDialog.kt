package com.kudashov.rabbits_farm.screens.farm.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.RabbitOperationsAdapter
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.data.ui.RabbitMoreInfUi
import com.kudashov.rabbits_farm.databinding.DialogFragmentRabbitMoreInfoBinding
import com.kudashov.rabbits_farm.screens.farm.Farm
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateRabbit
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_FATHER
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.RABBIT_TYPE_MATHER

class RabbitDialog : DialogFragment() {

    companion object {
        const val ARG_RABBIT: String = "rabbit"
        const val ARG_VIEW_MODEL: String = "view_model"

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
        Log.d(TAG, "onCreateView: Rabbit dialog is created")
        _binding = DialogFragmentRabbitMoreInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: Rabbit dialog is destroyed")
        _binding = null
    }

    private fun init() {
        Log.d(TAG, "init: Init dialog")
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.color.transparent
            )
        )

        viewModel = ViewModelProvider(this).get(RabbitViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        adapter = RabbitOperationsAdapter()
        recyclerView = binding.historyOfOperationList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        initListeners()
    }

    private fun initListeners() {
        binding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.btnWeigh.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(ARG_VIEW_MODEL, viewModel)
            bundle.putSerializable(ARG_RABBIT, arguments?.get(ARG_RABBIT) as RabbitMoreInfUi)

            val dialogWeigh = WeighDialog.newInstance(bundle)
            val transaction = parentFragmentManager.beginTransaction()
            dialogWeigh.show(transaction, "Dialog_Weigh")
        }

        binding.btnChangeType.setOnClickListener {
            Toast.makeText(requireContext(), "Меняем тип", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(rabbit: RabbitMoreInfUi) {
        binding.apply {
            Log.d(TAG, "loadData: ${rabbit.weight}")
            txtRabbitId.text = resources.getString(
                R.string.dialog_rabbit_txt_id,
                rabbit.id
            )
            if (rabbit.is_male != null) {
                if (rabbit.is_male)
                    icRabbitGender.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_gender_male_black
                        )
                    )
                else
                    icRabbitGender.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_gender_female_black
                        )
                    )

            }

            txtWeight.text = resources.getString(
                R.string.dialog_rabbit_txt_weight,
                rabbit.weight.toString()
            )

            txtBirthday.text = resources.getString(
                R.string.dialog_rabbit_txt_birthday,
                rabbit.birthday
            )

            txtAge.text = resources.getString(
                R.string.dialog_rabbit_txt_age,
                rabbit.age
            )

            txtType.text = resources.getString(
                R.string.dialog_rabbit_txt_type,
                rabbit.currentTypeString
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
            if (rabbit.currentType == RABBIT_TYPE_MATHER || rabbit.currentType == RABBIT_TYPE_FATHER) {
                txtCountOfPins.text =  resources.getString(
                    R.string.dialog_rabbit_txt_output,
                    rabbit.output.toString()
                )
                txtAveragePins.text =  resources.getString(
                    R.string.dialog_rabbit_txt_output_efficiency,
                    rabbit.output_efficiency.toString()
                )
            }
        }
    }

    private fun stateProcessing(state: StateRabbit) {
        when (state) {
            is StateRabbit.Default -> {
                Log.d(TAG, "stateProcessing: Rabbit Default")
                viewModel.getRabbitMoreInf(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
                viewModel.getOperations(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
                APP_ACTIVITY.hideLoader()
            }
            is StateRabbit.Sending -> {
                Log.d(TAG, "stateProcessing: Rabbit Sending")
                APP_ACTIVITY.showLoader()
            }
            is StateRabbit.SuccessRabbit -> {
                Log.d(TAG, "stateProcessing: Rabbit Success (Rabbit)")
                APP_ACTIVITY.hideLoader()
                arguments?.putSerializable(ARG_RABBIT, state.rabbit)
                loadData(state.rabbit)
            }
            is StateRabbit.SuccessOperations -> {
                Log.d(TAG, "stateProcessing: Rabbit Success (Operations)")
                APP_ACTIVITY.hideLoader()
                adapter.setList(state.operations)
            }
            is StateRabbit.Error<*> -> {
                Log.d(TAG, "stateProcessing: Rabbit Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
        }

    }
}