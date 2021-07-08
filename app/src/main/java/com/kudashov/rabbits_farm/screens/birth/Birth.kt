package com.kudashov.rabbits_farm.screens.birth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.databinding.FragmentBirthBinding
import com.kudashov.rabbits_farm.databinding.FragmentFarmBinding

class Birth : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentBirthBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }
}