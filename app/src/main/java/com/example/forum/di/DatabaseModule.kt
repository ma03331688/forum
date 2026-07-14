package com.example.forum.di

import android.content.Context
import androidx.room.Room
import com.example.forum.data.local.dao.CommentDao
import com.example.forum.data.local.dao.PostDao
import com.example.forum.data.local.dao.UserDao
import com.example.forum.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "forum_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao{
        return appDatabase.userDao()
    }
    @Provides
    @Singleton
    fun providePostDao(appDatabase: AppDatabase): PostDao{
        return appDatabase.postDao()
    }
    @Provides
    @Singleton
    fun provideCommentDao(appDatabase: AppDatabase): CommentDao{
        return appDatabase.commentDao()
    }
}