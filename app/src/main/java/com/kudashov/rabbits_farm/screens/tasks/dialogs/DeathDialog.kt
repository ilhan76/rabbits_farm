package com.kudashov.rabbits_farm.screens.tasks.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.kudashov.rabbits_farm.R
import com.kudashov.rabbits_farm.adapters.SpinnerAdapter
import com.kudashov.rabbits_farm.databinding.DialogFragmentDeathBinding
import com.kudashov.rabbits_farm.screens.tasks.TasksViewModel
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*

class DeathDialog : DialogFragment() {

    companion object {
        const val ARG_VIEW_MODEL: String = "viewModel"

        fun newInstance(bundle: Bundle): DeathDialog {
            val dialog = DeathDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    private val TAG: String = this::class.java.simpleName
    private var _binding: DialogFragmentDeathBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TasksViewModel
    private lateinit var adapter: SpinnerAdapter

    private lateinit var deathCause: List<String?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: Death Dialog")
        _binding = DialogFragmentDeathBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: Death Dialog")
        _binding = null
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

        viewModel = arguments?.get(ARG_VIEW_MODEL) as TasksViewModel

        deathCause = listOf(
            "",
            DEATH_CAUSES[DEATH_CAUSE_COLD],
            DEATH_CAUSES[DEATH_CAUSE_DISEASE],
            DEATH_CAUSES[DEATH_CAUSE_MOTHER],
            DEATH_CAUSES[DEATH_CAUSE_OVERHEATING],
            DEATH_CAUSES[DEATH_CAUSE_SLAUGHTER],
            DEATH_CAUSES[DEATH_CAUSE_SLAUGHTER],
            DEATH_CAUSES[DEATH_CAUSE_OTHER]
        )
        adapter = SpinnerAdapter(requireContext())
        adapter.setList(deathCause)
        binding.spDeathCause.adapter = adapter
        binding.spDeathCause.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var isNotEmpty = true
                for (i in DEATH_CAUSES){
                    if (i.value == deathCause[position]){
                        viewModel.setDeathCause(i.key)
                        isNotEmpty = false
                    }
                }
                if (isNotEmpty) viewModel.setDeathCause(null)
            }
        }
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            btnExit.setOnClickListener {
                dialog!!.dismiss()
            }

            btnSave.setOnClickListener {
                viewModel.putDeath(
                    editTxtNumberOfFarm.text.toString().toInt(),
                    editTxtNumberOfCage.text.toString().toInt(),
                    editTxtLetter.text.toString()
                )
                dialog!!.dismiss()
            }
        }
    }
}