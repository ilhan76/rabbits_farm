package com.kudashov.rabbits_farm.screens.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.adapters.TasksAdapter
import com.kudashov.rabbits_farm.databinding.FragmentTasksBinding
import com.kudashov.rabbits_farm.screens.farm.filters.cage.CageFilter
import com.kudashov.rabbits_farm.screens.farm.filters.rabbit.RabbitFilter
import com.kudashov.rabbits_farm.screens.tasks.dialogs.DeathDialog
import com.kudashov.rabbits_farm.utilits.PaginationScrollListener
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.StateTask
import com.kudashov.rabbits_farm.utilits.const.sort.*

class Tasks : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TasksViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksAdapter
    private lateinit var spinnerAdapter: SpinnerAdapter

    private val typesOfSort = listOf(
        "",
        TYPES_SORT_TASK[SORT_TASKS_DEPOSITION],
        TYPES_SORT_TASK[SORT_TASKS_DEPOSITION_FROM_MOTHER],
        TYPES_SORT_TASK[SORT_TASKS_REPRODUCTION],
        TYPES_SORT_TASK[SORT_TASKS_INSPECTION],
        TYPES_SORT_TASK[SORT_TASKS_VACCINATION],
        TYPES_SORT_TASK[SORT_TASKS_SLAUGHTER]
    )

    private var isDone: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        APP_ACTIVITY.moveUnderline(R.id.tasks)

        viewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        adapter = TasksAdapter()
        adapter.attachDelegate(viewModel)
        recyclerView = binding.rvTasks
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadNextPage() {
                Log.d(TAG, "loadMoreItems: NEXT PAGE")
                viewModel.nextPage()

                viewModel.getTasks(isDone)
            }
        })

        spinnerAdapter = SpinnerAdapter(requireContext())
        spinnerAdapter.setList(typesOfSort)
        binding.spinner.adapter = spinnerAdapter

        initListeners()
    }

    private fun initListeners() {
        binding.btnDeath.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(DeathDialog.ARG_VIEW_MODEL, viewModel)
            bundle.putSerializable("123", "4546")

            val dialogDeath = DeathDialog.newInstance(bundle)
            val transaction = parentFragmentManager.beginTransaction()
            dialogDeath.show(transaction, "Dialog_Death")
            Log.d(TAG, "initButtons: create")
        }

        binding.btnDone.setOnClickListener {
            isDone = true
            loadData()
        }

        binding.btnNotDone.setOnClickListener {
            isDone = false
            loadData()
        }

        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "onItemSelected: SPINNER")
                    viewModel.cleanPage()
                    var type: String? = null
                    for ((k, v) in TYPES_SORT_TASK)
                        if (typesOfSort[position] == v) type = k
                    viewModel.setOrderBy(type)
                }
            }
    }

    private fun loadData() {
        viewModel.cleanPage()

        if (isDone) {
            binding.btnDone.setBackgroundResource(R.drawable.shape_btn_green)
            binding.btnNotDone.setBackgroundResource(R.drawable.shape_btn_grey)
        } else {
            binding.btnDone.setBackgroundResource(R.drawable.shape_btn_grey)
            binding.btnNotDone.setBackgroundResource(R.drawable.shape_btn_green)
        }
        viewModel.getTasks(isDone)
    }

    private fun stateProcessing(state: StateTask) {
        when (state) {
            is StateTask.Default -> {
                Log.d(TAG, "stateProcessing: Tasks Default")
                loadData()
            }
            is StateTask.Sending -> {
                Log.d(TAG, "stateProcessing: Tasks Sending")
                binding.txtNoItem.visibility = View.GONE
                APP_ACTIVITY.showLoader()
            }
            is StateTask.ListOfTaskReceived -> {
                Log.d(TAG, "stateProcessing: Tasks Success")
                APP_ACTIVITY.hideLoader()
                binding.txtNoItem.visibility = View.GONE
                adapter.setList(state.list)
            }
            is StateTask.Error<*> -> {
                Log.d(TAG, "stateProcessing: Tasks Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                binding.txtNoItem.visibility = View.GONE
                APP_ACTIVITY.hideLoader()
            }
            StateTask.NoItem -> {
                Log.d(TAG, "stateProcessing: No Item")
                binding.txtNoItem.visibility = View.VISIBLE
                adapter.setList(emptyList())
                APP_ACTIVITY.hideLoader()
            }
            StateTask.NotAllFieldsAreFilledIn -> {
                Log.d(TAG, "stateProcessing: NotAllFieldsAreFilledIn")
                Toast.makeText(context, "Заполнены не все поля", Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}