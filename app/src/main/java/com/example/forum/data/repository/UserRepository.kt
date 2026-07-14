package com.example.forum.data.repository

import com.example.forum.data.local.dao.UserDao
import com.example.forum.data.local.entity.UserEntity
import com.example.forum.data.remote.service.NetworkClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()
    suspend fun getUsersList(): List<UserEntity> = userDao.getUsersList()
    suspend fun refreshUsers() {
        try {
            val users = NetworkClient.api.getUsers()
            val entities = users.map { dto ->
                UserEntity(
                    id = dto.id,
                    name = dto.name,
                    username = dto.username,
                    email = dto.email,
                    phone = dto.phone,
                    website = dto.website,
                    companyName = dto.company.name
                )
            }
            userDao.insertUsers(entities)
        } catch (e: Exception) {
            throw e
        }
    }
}