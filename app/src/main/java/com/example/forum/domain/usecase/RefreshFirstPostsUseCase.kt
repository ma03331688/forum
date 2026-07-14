package com.example.forum.domain.usecase

import com.example.forum.data.repository.PostRepository
import com.example.forum.data.repository.UserRepository
import com.example.forum.domain.model.PostWithAuthor
import com.example.forum.utils.toDomainWithAuthors
import javax.inject.Inject

class RefreshFirstPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(limit: Int = 10): List<PostWithAuthor> {
        val posts = postRepository.refreshFirstPosts(limit)
        val users = userRepository.getUsersList()
        val userMap = users.associateBy { it.id }

        return posts.toDomainWithAuthors(userMap)
    }
}