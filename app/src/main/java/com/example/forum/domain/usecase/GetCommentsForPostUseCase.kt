package com.example.forum.domain.usecase

import com.example.forum.data.repository.CommentRepository
import com.example.forum.domain.model.Comment
import com.example.forum.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCommentsForPostUseCase @Inject constructor(
    private val commentRepository: CommentRepository
){
    operator fun invoke(postId: Int): Flow<List<Comment>> {
        return commentRepository.getCommentsWithAuthorForPost(postId)
            .map { list ->
                list.map { it.toDomain() }
            }
    }
}