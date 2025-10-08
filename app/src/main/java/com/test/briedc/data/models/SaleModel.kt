package com.test.briedc.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SaleModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_id")
    val transactionId: Int
): Serializable