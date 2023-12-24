package com.example.homework17revised2.presentation.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework17revised2.R
import com.example.homework17revised2.common.BaseFragment
import com.example.homework17revised2.common.Resource
import com.example.homework17revised2.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val loginEvent: LoginEvent = LoginEvent.Login(email = email, password = password)
                loginViewModel.onEvent(loginEvent)
            }

            binding.btnRegister.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.Register)
            }
        }
    }

    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFlow.collect { result ->
                    when (result) {
                        is Resource.Success ->
                            Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                        is Resource.Error ->
                            Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.navigationFlow.collect { event ->
                    when(event){
                        is NavigationEvent.NavigateToHome -> navigateToHome()
                        is NavigationEvent.NavigateToRegister -> navigateToRegister()
                    }
                }
            }
        }
    }

    private fun navigateToHome(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navigateToRegister(){
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}