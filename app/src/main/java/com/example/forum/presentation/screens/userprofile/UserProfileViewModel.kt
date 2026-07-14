package com.example.forum.presentation.screens.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.forum.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId: Int = savedStateHandle["userId"] ?: 0
    private val _uiState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val uiState: StateFlow<UserProfileState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = UserProfileState.Loading
            getUserProfileUseCase(userId)
                .catch { error ->
                    _uiState.value = UserProfileState.Error(
                        message = error.message ?: "failed to load user profile"
                    )
                }
                .collect { user ->
                    _uiState.value = UserProfileState.Success(user)
                }
        }
    }

    fun refresh() {
        loadUserProfile()
    }
}