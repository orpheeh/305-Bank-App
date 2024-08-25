package xyz.norlib.bank305.business.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("balance") val balance: Int,
    @SerializedName("userKey") val userKey: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
)
