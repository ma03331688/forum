package com.example.forum.domain.usecase

import com.example.forum.data.repository.UserRepository
import com.example.forum.domain.model.User
import com.example.forum.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(userId: Int): Flow<User> {
        return userRepository.getUsers()
            .map { users ->
                val user =users.find { it.id == userId }
                user?.toDomain()
                    ?: throw IllegalArgumentException("user $userId not found")
            }
    }
}