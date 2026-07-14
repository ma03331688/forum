package com.example.forum.data.repository

import com.example.forum.data.local.dao.CommentDao
import com.example.forum.data.local.entity.CommentEntity
import com.example.forum.data.local.entity.CommentWithAuthor
import com.example.forum.data.remote.service.NetworkClient
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CommentRepository @Inject constructor(
    private val commentDao: CommentDao
) {
    fun getCommentsWithAuthorForPost(postId: Int): Flow<List<CommentWithAuthor>> =
        commentDao.getCommentsWithAuthorForPost(postId)

    suspend fun refreshCommentsForPost(postId: Int) {
        try {
            val commentsDto = NetworkClient.api.getComentsForPost(postId)
            val entities = commentsDto.map { dto ->
                CommentEntity(
                    id = dto.id,
                    postId = dto.postId,
                    authorName = dto.name,
                    authorEmail = dto.email,
                    body = dto.body
                )
            }
            commentDao.insertComments(entities)
        } catch (e: Exception) {
            throw e
        }
    }
}