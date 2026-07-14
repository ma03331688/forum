package com.example.forum.data.remote.model
import com.google.gson.annotations.SerializedName

/*{
    "postId": 1,
    "id": 1,
    "name": "id labore ex et quam laborum",
    "email": "Eliseo@gardner.biz",
    "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
},*/

data class CommentDto (
    @SerializedName("postId")
    val postId:Int,

    @SerializedName("id")
    val id:Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("body")
    val body: String
)