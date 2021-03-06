package com.kudashov.rabbits_farm.screens.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.kudashov.rabbits_farm.databinding.FragmentAuthBinding
import com.kudashov.rabbits_farm.screens.auth.delegate.AuthNavigationDelegate
import com.kudashov.rabbits_farm.utilits.StateAuth
import com.kudashov.rabbits_farm.utilits.const.APP_ACTIVITY
import com.kudashov.rabbits_farm.utilits.const.messages.MESSAGE_INVALID_PASS
import com.kudashov.rabbits_farm.utilits.const.messages.MESSAGE_USER_DOES_NOT_EXIST

class Auth : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var navigation: AuthNavigationDelegate
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        navigation = activity as AuthNavigationDelegate
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
        }
    }

    private fun stateProcessing(state: StateAuth) {
        when (state) {
            StateAuth.Default -> {
                Log.d(TAG, "stateProcessing: Auth Default")
                viewModel.echo()
                APP_ACTIVITY.hideLoader()
            }
            StateAuth.Sending -> {
                Log.d(TAG, "stateProcessing: Auth Sending")
                APP_ACTIVITY.showLoader()
            }
            is StateAuth.Success -> {
                Log.d(TAG, "stateProcessing: Auth Success: ${state.message}")
                APP_ACTIVITY.hideLoader()
                navigation.logIn()
            }
            StateAuth.OutdatedToken -> {
                Log.d(TAG, "stateProcessing: ?????????? ??????????????")
                binding.auth.visibility = View.VISIBLE
                APP_ACTIVITY.hideLoader()
            }
            is StateAuth.Error<*> -> {
                Log.d(TAG, "stateProcessing: Auth Error ${state.message.toString()}")
                Toast.makeText(context, state.message.toString(), Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
            StateAuth.ActualToken -> {
                Log.d(TAG, "stateProcessing: Actual Token")
                navigation.logIn()
            }
            StateAuth.InvalidPass -> {
                Log.d(TAG, "stateProcessing: InvalidPass")
                Toast.makeText(context, MESSAGE_INVALID_PASS, Toast.LENGTH_SHORT).show()
                APP_ACTIVITY.hideLoader()
            }
            StateAuth.UserDoesNotExist -> {
                Log.d(TAG, "stateProcessing: UserDoesNotExist")
                Toast.makeText(
                    context,
                    MESSAGE_USER_DOES_NOT_EXIST,
                    Toast.LENGTH_SHORT
                ).show()
                APP_ACTIVITY.hideLoader()
            }
        }
    }
}