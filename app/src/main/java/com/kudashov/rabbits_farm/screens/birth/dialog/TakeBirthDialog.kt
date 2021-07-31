package com.kudashov.rabbits_farm.screens.birth.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.DialogFragmentTakeBirthsBinding
import com.kudashov.rabbits_farm.screens.birth.Birth
import com.kudashov.rabbits_farm.screens.birth.BirthViewModel

class TakeBirthDialog : DialogFragment() {

    companion object {
        fun newInstance(bundle: Bundle): TakeBirthDialog {
            val dialog = TakeBirthDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    private var _binding: DialogFragmentTakeBirthsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BirthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentTakeBirthsBinding.inflate(layoutInflater, container, false)
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

        viewModel = arguments?.getSerializable(Birth.ARG_VIEW_MODEL) as BirthViewModel

        initButtons()
    }

    private fun initButtons() {
        binding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.btnSave.setOnClickListener {
            viewModel.takeBirth(
                arguments?.get(Birth.ARG_ID) as Int,
                binding.txtCount.text.toString().toInt()
            )
            dialog!!.dismiss()
        }
    }
}