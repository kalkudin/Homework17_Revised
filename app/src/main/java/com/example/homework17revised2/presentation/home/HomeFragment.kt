package com.example.homework17revised2.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework17revised2.R
import com.example.homework17revised2.common.BaseFragment
import com.example.homework17revised2.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel : HomeFragmentViewModel by viewModels()
    override fun bind() {
    }

    override fun bindViewActionListeners() {
        with(binding){
            btnLogout.setOnClickListener(){
                homeViewModel.onEvent(HomeEvent.Logout)
            }
        }
    }

    override fun bindObservers() {
        displayEmail()
        displayToken()

        viewLifecycleOwner.lifecycleScope.launch(){
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.homeFragmentFlow.collect(){ event ->
                    when(event){
                        is HomeNavigationEvent.Logout -> navigateToLogin()
                    }
                }
            }
        }
    }

    private fun displayEmail(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.userEmail.collect(){
                    binding.tvEmail.text = it
                }
            }
        }
    }

    private fun displayToken(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.userName.collect(){
                    binding.tvToken.text = it
                }
            }
        }
    }

    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}