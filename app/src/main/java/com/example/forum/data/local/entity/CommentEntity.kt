package com.example.forum.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("postId")]
    )
data class CommentEntity (
    @PrimaryKey
    val id:Int,
    val postId:Int,
    val authorName: String,
    val authorEmail: String,
    val body:String
)