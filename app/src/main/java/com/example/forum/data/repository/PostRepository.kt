package com.example.forum.data.repository

import com.example.forum.data.local.dao.PostDao
import com.example.forum.data.local.entity.PostEntity
import com.example.forum.data.remote.service.NetworkClient
import com.example.forum.utils.toEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PostRepository @Inject constructor(
    private val postDao: PostDao
) {
    fun getPosts(): Flow<List<PostEntity>> = postDao.getAllPosts()
    suspend fun fetchAndSaveFirstPosts(limit: Int = 10): List<PostEntity> {
        val posts = NetworkClient.api.getPostsPaginated(0, limit)
        val entities = posts.map { it.toEntity() }
        postDao.insertPosts(entities)
        return entities
    }
    suspend fun fetchAndSaveNextPosts(offset: Int, limit: Int = 10): List<PostEntity> {
        val posts = NetworkClient.api.getPostsPaginated(offset, limit)
        val entities = posts.map { it.toEntity() }
        postDao.insertPosts(entities)
        return entities
    }
    suspend fun refreshFirstPosts(limit: Int = 10): List<PostEntity> {
        val posts = NetworkClient.api.getPostsPaginated(0, limit)
        val entities = posts.map { it.toEntity() }
        postDao.insertPosts(entities)
        return entities
    }
}