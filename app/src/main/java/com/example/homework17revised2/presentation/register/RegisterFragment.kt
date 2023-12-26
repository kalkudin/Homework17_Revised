package com.example.homework17revised2.presentation.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework17revised2.R
import com.example.homework17revised2.common.BaseFragment
import com.example.homework17revised2.data.resource.Resource
import com.example.homework17revised2.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Thread.State

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel : RegisterViewModel by viewModels()
    override fun bind() {
    }

    override fun bindViewActionListeners() {
        with(binding){
            btnRegister.setOnClickListener(){
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val repeatPassword = etRepeatPassword.text.toString()
                registerViewModel.onEvent(RegisterEvent.Register(email = email, password = password, repeatPassword = repeatPassword))
            }
        }
    }

    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.registerFlow.collect(){ result ->
                    when(result) {
                        is Resource.Success ->
                            Toast.makeText(requireContext(), "Registration Success!", Toast.LENGTH_SHORT).show()
                        is Resource.Error ->
                            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()

                        else -> {

                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.navigationFlow.collect(){ navigationEvent ->
                    when(navigationEvent){
                        is RegisterNavigationEvent.NavigateToLogin -> navigateToLogin()
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}