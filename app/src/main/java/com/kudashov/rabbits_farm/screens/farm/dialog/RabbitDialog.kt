package com.kudashov.rabbits_farm.screens.farm.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.RabbitOperationsAdapter
import com.kudashov.rabbits_farm.data.domain.OperationDomain
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfDomain
import com.kudashov.rabbits_farm.databinding.DialogFragmentRabbitMoreInfoBinding
import com.kudashov.rabbits_farm.screens.farm.Farm
import com.kudashov.rabbits_farm.utilits.BaseListState
import com.kudashov.rabbits_farm.utilits.BaseState
import com.kudashov.rabbits_farm.utilits.RecastState
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
    private var isRecast: Boolean = false

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
        viewModel.rabbitState.observe(viewLifecycleOwner, this::rabbitProcessing)
        viewModel.operationState.observe(viewLifecycleOwner, this::operationsProcessing)
        viewModel.isRecastState.observe(viewLifecycleOwner, this::isRecastProcessing)

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
            bundle.putSerializable(ARG_RABBIT, arguments?.get(ARG_RABBIT) as RabbitMoreInfDomain)

            val dialogWeigh = WeighDialog.newInstance(bundle)
            val transaction = parentFragmentManager.beginTransaction()
            dialogWeigh.show(transaction, "Dialog_Weigh")
        }

        binding.btnChangeType.setOnClickListener {
            if (!isRecast) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Создать задачу на смену типа?")
                    .setCancelable(true)
                    .setPositiveButton("Да") { dialog, _ ->
                        viewModel.createRecast(
                            arguments?.get(Farm.ARG_RABBIT_ID) as Int,
                            arguments?.get(Farm.ARG_RABBIT_TYPE) as String
                        )
                        dialog.cancel()
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage("Отменить задачу на смену типа?")
                    .setCancelable(true)
                    .setPositiveButton("Да") { dialog, _ ->
                        viewModel.deleteRecast(
                            arguments?.get(Farm.ARG_RABBIT_ID) as Int,
                            arguments?.get(Farm.ARG_RABBIT_TYPE) as String
                        )
                        dialog.cancel()
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        }
    }

    private fun loadData(rabbit: RabbitMoreInfDomain) {
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

    private fun rabbitProcessing(state: BaseState) {
        when (state) {
            BaseState.Default -> {
                Log.d(TAG, "rabbitProcessing: Default")
                viewModel.getRabbitMoreInf(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
            }
            BaseState.Sending -> {
                Log.d(TAG, "rabbitProcessing: Loading")
            }
            is BaseState.Success<*> -> {
                Log.d(TAG, "rabbitProcessing: Success")
                loadData(state.content as RabbitMoreInfDomain)
            }
            is BaseState.Error<*> -> {
                Log.d(TAG, "rabbitProcessing: Error")
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun operationsProcessing(state: BaseListState) {
        when (state) {
            BaseListState.Default -> {
                Log.d(TAG, "operationsProcessing: Default")
                viewModel.getOperations(arguments?.get(Farm.ARG_RABBIT_ID) as Int)
            }
            BaseListState.Sending -> {
                Log.d(TAG, "operationsProcessing: Sending")
            }
            is BaseListState.Success<*> -> {
                Log.d(TAG, "operationsProcessing: Success")
                @Suppress("UNCHECKED_CAST")
                adapter.setList(state.content as List<OperationDomain>)
            }
            BaseListState.NoItem -> {
                Log.d(TAG, "operationsProcessing: NoItem")
                Toast.makeText(context, "Список операций пуст", Toast.LENGTH_SHORT).show()
            }
            is BaseListState.Error<*> -> {
                Log.d(TAG, "operationsProcessing: Error")
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isRecastProcessing(state: RecastState) {
        when (state) {
            RecastState.Default -> {
                Log.d(TAG, "isRecastProcessing: Default")
                viewModel.isRecast(
                    arguments?.get(Farm.ARG_RABBIT_ID) as Int,
                    arguments?.get(Farm.ARG_RABBIT_TYPE) as String
                )
            }
            RecastState.Sending -> {
                Log.d(TAG, "isRecastProcessing: Sending")
            }
            is RecastState.IsRecast -> {
                Log.d(TAG, "isRecastProcessing: IsRecast")
                binding.btnChangeType.visibility = View.VISIBLE
                isRecast = state.content
                if (!isRecast) {
                    binding.btnChangeType.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_btn_change_type_blue
                        )
                    )
                } else {
                    binding.btnChangeType.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_btn_change_type_red
                        )
                    )
                }
            }
            RecastState.Success -> {
                Log.d(TAG, "isRecastProcessing: Success")
            }
            RecastState.Bunny -> {
                binding.btnChangeType.visibility = View.GONE
            }
            is RecastState.Error<*> -> {
                Log.d(TAG, "isRecastProcessing: Error")
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}