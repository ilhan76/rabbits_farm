package com.kudashov.rabbits_farm.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.databinding.FragmentAuthBinding
import com.kudashov.rabbits_farm.screens.AuthNavigation
import com.kudashov.rabbits_farm.utilits.StateAuth

class Auth : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigation: AuthNavigation
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        navigation = activity as AuthNavigation
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.getStates().observe(this, this::stateProcessing)

        binding.btnAuth.setOnClickListener {
            viewModel.auth(
                binding.editTxtEmail.text.toString(),
                binding.editTxtPass.text.toString()
            )
            //viewModel.echo()
        }
    }

    private fun stateProcessing(state: StateAuth) {
        when (state) {
            is StateAuth.Default -> {
                Toast.makeText(context, "Auth Default", Toast.LENGTH_SHORT).show()
            }
            is StateAuth.Sending -> {
                Toast.makeText(context, "Auth Sending", Toast.LENGTH_SHORT).show()
                //todo - добавить лоадер
            }
            is StateAuth.Success -> {
                Toast.makeText(context, "Auth Success", Toast.LENGTH_SHORT).show()
                navigation.auth()
            }
            is StateAuth.OutdatedToken -> {
                Toast.makeText(context, "Токен устарел", Toast.LENGTH_SHORT).show()
            }
            is StateAuth.Error<*> -> {
                Toast.makeText(context, "Auth ${state.message.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }
}