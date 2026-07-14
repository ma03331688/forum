package com.example.forum.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forum.data.local.dao.CommentDao
import com.example.forum.data.local.dao.PostDao
import com.example.forum.data.local.dao.UserDao
import com.example.forum.data.local.entity.CommentEntity
import com.example.forum.data.local.entity.PostEntity
import com.example.forum.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        CommentEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract  class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

}