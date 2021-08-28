package com.kudashov.rabbits_farm.screens.farm.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfDomain
import com.kudashov.rabbits_farm.databinding.DialogFragmentWeighRabbitBinding

class WeighDialog : DialogFragment() {

    companion object {
        fun newInstance(bundle: Bundle): WeighDialog {
            val dialog = WeighDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    private var _binding: DialogFragmentWeighRabbitBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RabbitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentWeighRabbitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.color.transparent
            )
        )
        viewModel = arguments?.get(RabbitDialog.ARG_VIEW_MODEL) as RabbitViewModel

        binding.txtCurrentWeight.text = resources.getString(
            R.string.dialog_weight_txt_current_weight,
            (arguments?.get(RabbitDialog.ARG_RABBIT) as RabbitMoreInfDomain).weight.toString()
        )
        initButtons()
    }

    private fun initButtons() {
        binding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.btnSave.setOnClickListener {
            val rabbit = arguments?.get(RabbitDialog.ARG_RABBIT) as RabbitMoreInfDomain
            Log.d("TAG", "initButtons: $rabbit")
            viewModel.postWeight(
                weight = binding.editTxtNewWeight.text.toString().toDouble(),
                id = rabbit.id,
                type = rabbit.currentTypeString
            )
            dialog!!.dismiss()
        }
    }
}