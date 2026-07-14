package com.example.forum.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class PostEntity (
    @PrimaryKey
    val id:Int,
    val userId:Int,
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis()
)