package com.test.briedc.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginModel(
    @SerializedName("token")
    val token: String
) : Serializable