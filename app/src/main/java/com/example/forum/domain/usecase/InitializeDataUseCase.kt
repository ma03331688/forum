package com.example.forum.domain.usecase

import com.example.forum.data.repository.UserRepository
import javax.inject.Inject

class InitializeDataUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(){
        try {
            userRepository.refreshUsers()
        } catch (e: Exception) {
        }
    }
}