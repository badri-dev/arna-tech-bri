package com.test.briedc.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryModel(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("UpdatedAt")
    val updatedAt: String?,
    @SerializedName("DeletedAt")
    val deletedAt: String?,
    @SerializedName("UserID")
    val userId: Int,
    @SerializedName("MerchantID")
    val merchantId: String,
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("CardNumber")
    val cardNumber: String,
    @SerializedName("Status")
    val status: String
) : Serializable