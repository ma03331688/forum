package com.example.forum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forum.data.local.entity.CommentEntity
import com.example.forum.data.local.entity.CommentWithAuthor
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT * FROM comments WHERE postId=:postId ORDER BY id ASC")
    fun getCommentsForPost(postId:Int): Flow<List<CommentEntity>>

    @Query("""
        SELECT 
            comments.id AS commentId,
            comments.postId AS commentPostId,
            comments.authorName AS commentAuthorName,
            comments.authorEmail AS commentAuthorEmail,
            comments.body AS commentBody
        FROM comments
        WHERE comments.postId = :postId
        ORDER BY comments.id ASC
    """)
    fun getCommentsWithAuthorForPost(postId: Int): Flow<List<CommentWithAuthor>>

}