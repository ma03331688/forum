package com.example.forum.data.remote.model
import com.google.gson.annotations.SerializedName

/*{
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
}
},
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
    "name": "Romaguera-Crona",
}
},*/

data class UserDto (
    @SerializedName("id")
    val id:Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("website")
    val website: String,

    @SerializedName("company")
    val company:CompanyDto
)
data class CompanyDto(
    @SerializedName("name")
    val name: String
)