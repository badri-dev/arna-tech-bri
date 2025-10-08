package com.test.briedc.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SettlementModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("records_updated")
    val records_updated: Int
): Serializable