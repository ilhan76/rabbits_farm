package com.kudashov.rabbits_farm.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FloatingActionFragmentRabbitMoreInfoBinding
import com.kudashov.rabbits_farm.utilits.RH

class Rabbit: DialogFragment() {

    private var _binding: FloatingActionFragmentRabbitMoreInfoBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_item_corner)
        _binding = FloatingActionFragmentRabbitMoreInfoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
        // return inflater.inflate(R.layout.floating_action_fragment_rabbit_more_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        // val height = (resources.displayMetrics.heightPixels * 1).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog!!.window?.setBackgroundDrawable(RH.drawable(R.color.transparent))
    }
}