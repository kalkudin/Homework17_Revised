package com.example.homework17revised2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17revised2.domain.datastore.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val userDataStoreRepository: UserDataStoreRepository) :
    ViewModel() {

    private val _homeFragmentFlow = MutableSharedFlow<HomeNavigationEvent>()
    val homeFragmentFlow: SharedFlow<HomeNavigationEvent> = _homeFragmentFlow.asSharedFlow()

    var userName: StateFlow<String?> = userDataStoreRepository.readEmail()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = " ")
    var userEmail: StateFlow<String?> = userDataStoreRepository.readEmail()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = " ")

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch() {
            clearDataStore()
            _homeFragmentFlow.emit(HomeNavigationEvent.Logout)
        }
    }

    private fun clearDataStore() = run { viewModelScope.launch { userDataStoreRepository.clear() } }
}

sealed class HomeNavigationEvent {
    data object Logout : HomeNavigationEvent()
}