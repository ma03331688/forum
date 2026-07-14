package com.example.forum.domain.usecase

import com.example.forum.data.repository.PostRepository
import com.example.forum.data.repository.UserRepository
import com.example.forum.domain.model.PostWithAuthor
import com.example.forum.utils.toDomainWithAuthors
import javax.inject.Inject

class GetNextPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int = 10): List<PostWithAuthor> {
        val posts = postRepository.fetchAndSaveNextPosts(offset, limit)
        val users = userRepository.getUsersList()
        val userMap = users.associateBy { it.id }

        return posts.toDomainWithAuthors(userMap)
    }
}