package com.example.forum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forum.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers():Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id=:userId")
    suspend fun getUserById(userId: Int): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getUsersList(): List<UserEntity>
}