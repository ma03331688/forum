package com.example.forum.domain.usecase

import com.example.forum.data.repository.PostRepository
import com.example.forum.data.repository.UserRepository
import com.example.forum.domain.model.PostWithAuthor
import com.example.forum.utils.toDomainWithAuthors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPostsWithAuthorsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<PostWithAuthor>> {
        return combine(
            postRepository.getPosts(),
            userRepository.getUsers()
        ) { posts, users ->
            val userMap = users.associateBy { it.id }
            posts.toDomainWithAuthors(userMap)
        }
    }
}