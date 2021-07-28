package com.kudashov.rabbits_farm.screens.birth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.BirthAdapter
import com.kudashov.rabbits_farm.adapters.delegates.BirthDelegate
import com.kudashov.rabbits_farm.databinding.FragmentBirthBinding
import com.kudashov.rabbits_farm.screens.birth.dialog.TakeBirthDialog
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateBirth

class Birth : Fragment(), BirthDelegate {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentBirthBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mViewModel: BirthViewModel

    private lateinit var adapter: BirthAdapter
    private lateinit var recyclerView: RecyclerView

    private var isConfirmed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.birth)

        adapter = BirthAdapter()
        recyclerView = mBinding.birthList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        mViewModel = ViewModelProvider(this).get(BirthViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        initButtons()

        mViewModel.getTasks(isConfirmed)
        mBinding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)
    }

    private fun initButtons() {
        mBinding.btnConfirmed.setOnClickListener {
            isConfirmed = true
            mBinding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)
            mBinding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)

            mViewModel.getTasks(isConfirmed)
        }

        mBinding.btnNotYetConfirmed.setOnClickListener {
            isConfirmed = false
            mBinding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)
            mBinding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)

            mViewModel.getTasks(isConfirmed)
        }
    }

    private fun stateProcessing(state: StateBirth){
        when (state){
            is StateBirth.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
            }
            is StateBirth.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo - добавить лоадер
            }
            is StateBirth.ListOfBirthReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
                adapter.attachDelegate(this)
            }
            is StateBirth.Error<*> -> {
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openBirthDialog() {
        val dialogBirth =
            TakeBirthDialog()
        val transaction = parentFragmentManager.beginTransaction()
        dialogBirth.show(transaction, "Dialog_TakeBirth")
    }
}