package com.example.forum.data.remote.api

import com.example.forum.data.remote.model.CommentDto
import com.example.forum.data.remote.model.PostDto
import com.example.forum.data.remote.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*https://jsonplaceholder.typicode.com/users
https://jsonplaceholder.typicode.com/users/1
https://jsonplaceholder.typicode.com/posts
https://jsonplaceholder.typicode.com/posts/1
https://jsonplaceholder.typicode.com/comments
https://jsonplaceholder.typicode.com/comments/1*/

interface JsonPlaceholderApi {
    @GET ("users")
    suspend fun getUsers(): List<UserDto>

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPosts(@Path("id")id:Int): PostDto

    @GET("posts/{postId}/comments")
    suspend fun getComentsForPost(@Path("postId")postId: Int): List<CommentDto>

    @GET("posts")
    suspend fun getPostsPaginated(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<PostDto>

}