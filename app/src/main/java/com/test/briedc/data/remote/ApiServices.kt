package com.test.briedc.data.remote

import com.google.gson.JsonObject
import com.test.briedc.data.models.HistoryModel
import com.test.briedc.data.models.LoginModel
import com.test.briedc.data.models.RegisterModel
import com.test.briedc.data.models.SaleModel
import com.test.briedc.data.models.SettlementModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    /** Auth **/
    @POST("auth/login")
    suspend fun login(
        @Body body: JsonObject
    ): LoginModel

    @POST("auth/register")
    suspend fun register(
        @Body body: JsonObject
    ): RegisterModel

    /** Transaction **/
    @GET("transactions/history")
    suspend fun getTransactionHistory(): List<HistoryModel>

    @POST("transactions/sale")
    suspend fun postSale(
        @Body body: JsonObject
    ): SaleModel

    @POST("transactions/settlement")
    suspend fun postSettlement(
        @Body body: JsonObject
    ): SettlementModel
}