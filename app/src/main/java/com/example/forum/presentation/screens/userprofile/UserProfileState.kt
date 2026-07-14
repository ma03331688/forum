package com.example.forum.presentation.screens.userprofile

import com.example.forum.domain.model.User

sealed class UserProfileState {
    data object Loading : UserProfileState()
    data class Success(val user: User) : UserProfileState()
    data class Error(val message: String) : UserProfileState()
}