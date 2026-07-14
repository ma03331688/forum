package com.example.forum.utils

import com.example.forum.data.local.entity.CommentEntity
import com.example.forum.data.local.entity.CommentWithAuthor
import com.example.forum.data.local.entity.PostEntity
import com.example.forum.data.local.entity.UserEntity
import com.example.forum.data.remote.model.CommentDto
import com.example.forum.data.remote.model.PostDto
import com.example.forum.data.remote.model.UserDto
import com.example.forum.domain.model.Comment
import com.example.forum.domain.model.Post
import com.example.forum.domain.model.User
import com.example.forum.domain.model.PostWithAuthor

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id=id,
        name=name,
        username=username,
        email = email,
        phone = phone,
        website=website,
        companyName = company.name
    )
}
fun PostDto.toEntity(): PostEntity{
    return PostEntity(
        id=id,
        userId=userId,
        title=title,
        body=body,
        createdAt = System.currentTimeMillis()
    )
}
fun CommentDto.toEntity(): CommentEntity{
    return CommentEntity(
        id=id,
        postId=postId,
        authorName = name,
        authorEmail = email,
        body=body
    )
}
fun UserEntity.toDomain(): User {
    return User(
        id=id,
        name = name,
        username = username,
        email=email,
        phone = phone,
        website=website,
        companyName = companyName

    )
}
fun PostEntity.toDomain(): Post{
    return Post(
        id=id,
        userId=userId,
        title=title,
        body=body,
        createdAt=createdAt
    )
}
fun PostEntity.toDomainWithAuthor(user: UserEntity?): PostWithAuthor{
    return PostWithAuthor(
        post = toDomain(),
        authorName = user?.name ?: "unknown"
    )
}
fun CommentWithAuthor.toDomain(): Comment {
    return Comment(
        id = commentId,
        postId = commentPostId,
        authorName = commentAuthorName,
        authorEmail = commentAuthorEmail,
        body = commentBody
    )
}
fun List<PostEntity>.toDomainWithAuthors(userMap: Map<Int, UserEntity>): List<PostWithAuthor> {
    return this.map { post ->
        val author = userMap[post.userId]
        post.toDomainWithAuthor(author)
    }
}
