package com.kudashov.rabbits_farm.screens.farm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.FarmAdapter
import com.kudashov.rabbits_farm.adapters.delegates.FarmDelegate
import com.kudashov.rabbits_farm.data.item.Rabbit
import com.kudashov.rabbits_farm.databinding.FragmentFarmBinding
import com.kudashov.rabbits_farm.screens.dialogs.RabbitDialog
import com.kudashov.rabbits_farm.utilits.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateAboutFarm

class Farm: Fragment(), FarmDelegate {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentFarmBinding? = null
    private val binding get() = _binding!!

    private lateinit var mViewModel: FarmViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FarmAdapter

    private var isRabbit: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFarmBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.farm)

        adapter = FarmAdapter()
        recyclerView = binding.farmList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.attachDelegate(this)

        mViewModel = ViewModelProvider(this).get(FarmViewModel::class.java)
        mViewModel.getStates().observe(this, this::stateProcessing)

        initButtons()

        mViewModel.getRabbits()
    }

    private fun initButtons() {
        binding.btnToMenu.setOnClickListener {
            if (isRabbit)
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuRabbit)
            else
                APP_ACTIVITY.navController.navigate(R.id.action_farm_to_farmMenuCage)
        }

        binding.btnRabbits.setOnClickListener{
            isRabbit = true
            binding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_green)
            binding.btnCages.setBackgroundResource(R.drawable.shape_btn_grey)

            binding.buttonsPanel.visibility = View.GONE

            binding.txtListTitle1.text = resources.getString(R.string.farm_rabbits_txt_number_of_cage)
            binding.txtListTitle2.text = resources.getString(R.string.farm_rabbits_txt_age)
            binding.txtListTitle3.text = resources.getString(R.string.farm_rabbits_txt_gender)
            binding.txtListTitle4.text = resources.getString(R.string.farm_rabbits_txt_type)

            mViewModel.getRabbits()
        }
        binding.btnCages.setOnClickListener{
            isRabbit = false
            binding.btnCages.setBackgroundResource(R.drawable.shape_btn_green)
            binding.btnRabbits.setBackgroundResource(R.drawable.shape_btn_grey)

            binding.buttonsPanel.visibility = View.VISIBLE

            binding.txtListTitle1.text = resources.getString(R.string.farm_cages_txt_number_of_farm)
            binding.txtListTitle2.text = resources.getString(R.string.farm_cages_txt_number)
            binding.txtListTitle3.text = resources.getString(R.string.farm_cages_txt_type)
            binding.txtListTitle4.text = resources.getString(R.string.farm_cages_txt_status)


            mViewModel.getCages()
        }
    }

    private fun stateProcessing(state: StateAboutFarm){
        when (state){
            is StateAboutFarm.Default -> {
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
            }
            is StateAboutFarm.Sending ->{
                Toast.makeText(context, "Sending", Toast.LENGTH_SHORT).show()
                //todo - добавить лоадер
            }
            is StateAboutFarm.ListOfRabbitsReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
            }
            is StateAboutFarm.ListOfCageReceived -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                adapter.setList(state.list)
            }
            is StateAboutFarm.Error<*> -> {
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openMoreRabbitInfo(rabbit: Rabbit) {
        Toast.makeText(context, "Нажали на элемент списка", Toast.LENGTH_SHORT).show()

        val rabbitDialog = RabbitDialog()
        val transaction = parentFragmentManager.beginTransaction()
        rabbitDialog.show(transaction,"Rabbit")
    }
}