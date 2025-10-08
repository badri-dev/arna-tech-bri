package com.test.briedc.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterModel(
    @SerializedName("message")
    val message: String
): Serializable