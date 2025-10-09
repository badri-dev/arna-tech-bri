package com.test.briedc.data.models

import java.io.Serializable

data class DetailTransaction (
    val merchantId: String,
    val amount: Int
): Serializable