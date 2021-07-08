package com.kudashov.rabbits_farm.screens.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kudashov.rabbits_farm.databinding.FragmentTasksBinding

class Tasks : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentTasksBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

}