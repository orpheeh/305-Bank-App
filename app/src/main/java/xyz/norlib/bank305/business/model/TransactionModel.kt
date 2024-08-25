package xyz.norlib.bank305.business.model

import com.google.gson.annotations.SerializedName

data class TransactionModel(
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("destinataire") val destinataire: String,
    @SerializedName("destinataireDetail") val destinataireDetail: String?,
    @SerializedName("name") val name: String? = null,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("user") val user: String,
)