package com.kudashov.rabbits_farm.screens.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FloatingActionFragmentDeathBinding
import com.kudashov.rabbits_farm.utilits.RH

class DeathDialog: DialogFragment() {
    private var _binding: FloatingActionFragmentDeathBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FloatingActionFragmentDeathBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setBackgroundDrawable(RH.drawable(R.color.transparent))

        initButtons()
    }

    private fun initButtons() {
        mBinding.btnExit.setOnClickListener {
            dialog!!.dismiss()
        }

        mBinding.btnSave.setOnClickListener {
            // TODO - запрос к серверу на сохранение данных
            dialog!!.dismiss()
        }
    }
}