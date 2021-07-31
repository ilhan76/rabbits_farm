package com.kudashov.rabbits_farm.screens.birth

import android.os.Bundle
import android.util.Log
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
import com.kudashov.rabbits_farm.utilits.PaginationScrollListener
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateBirth

class Birth : Fragment(), BirthDelegate {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentBirthBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BirthViewModel

    private lateinit var adapter: BirthAdapter
    private lateinit var recyclerView: RecyclerView

    private var isConfirmed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.birth)

        adapter = BirthAdapter()
        adapter.attachDelegate(this)
        recyclerView = binding.birthList
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadNextPage() {
                Log.d(TAG, "loadMoreItems: NEXT PAGE")
                viewModel.nextPage()

                if (isConfirmed)
                    viewModel.getBirth(isConfirmed, null)
                else
                    viewModel.getBirth(isConfirmed, null)
            }
        })

        viewModel = ViewModelProvider(this).get(BirthViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        initListeners()
    }

    private fun initListeners() {
        binding.btnConfirmed.setOnClickListener {
            isConfirmed = true
            loadData()
        }

        binding.btnNotYetConfirmed.setOnClickListener {
            isConfirmed = false
            loadData()
        }
    }

    private fun loadData(){
        viewModel.cleanPage()

        if (isConfirmed){
            binding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)
            binding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)

            viewModel.getBirth(isConfirmed, null)
        } else {
            binding.btnConfirmed.setBackgroundResource(R.drawable.shape_btn_grey)
            binding.btnNotYetConfirmed.setBackgroundResource(R.drawable.shape_btn_orange)

            viewModel.getBirth(isConfirmed, null)
        }
    }

    private fun stateProcessing(state: StateBirth){
        when (state){
            is StateBirth.Default -> {
                Log.d(TAG, "stateProcessing: Birth Default")
                loadData()
                APP_ACTIVITY.hideLoader()
            }
            is StateBirth.Sending ->{
                Log.d(TAG, "stateProcessing: Birth Sending")
                APP_ACTIVITY.showLoader()
            }
            is StateBirth.ListOfBirthReceived -> {
                Log.d(TAG, "stateProcessing: Birth Success")
                adapter.setList(state.list)
                APP_ACTIVITY.hideLoader()
            }
            is StateBirth.Error<*> -> {
                Log.d(TAG, "stateProcessing: Birth Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openBirthDialog() {
        val dialogBirth = TakeBirthDialog()
        val transaction = parentFragmentManager.beginTransaction()
        dialogBirth.show(transaction, "Dialog_TakeBirth")
    }

    override fun confirmPregnancy(id: Int, isConfirmed: Boolean) {
        viewModel.confirmPregnancy(id, isConfirmed)
    }

    override fun takeBirth(id: Int, bornBunnies: Int) {
        TODO("Not yet implemented")
    }
}