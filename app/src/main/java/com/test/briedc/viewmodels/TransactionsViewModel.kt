package com.test.briedc.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.test.briedc.data.models.HistoryModel
import com.test.briedc.data.models.SaleModel
import com.test.briedc.data.models.SettlementModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.data.remote.RetrofitClient
import com.test.briedc.data.repository.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {
    private val repo = TransactionsRepository(RetrofitClient.apiService)

    // Data State for our collectors history transactions
    private val _stateTransactionHistory = MutableStateFlow<UIStateResponse<List<HistoryModel>>>(UIStateResponse.Loading)
    val stateTransactionHistory: StateFlow<UIStateResponse<List<HistoryModel>>> = _stateTransactionHistory
    fun loadTransactionHistory(token: String) {
        _stateTransactionHistory.value = UIStateResponse.Loading
        viewModelScope.launch {
            val resp = repo.getTransactionsHistory(token)
            if (resp.isSuccess) {
                _stateTransactionHistory.value = UIStateResponse.Success(resp.getOrNull() ?: emptyList())
            } else {
                _stateTransactionHistory.value = UIStateResponse.Error(resp.exceptionOrNull()?.message ?: "")
            }
        }
    }

    // Data State for our collectors post sale
    private val _stateSale = MutableStateFlow<UIStateResponse<SaleModel?>>(UIStateResponse.Loading)
    val stateSale: StateFlow<UIStateResponse<SaleModel?>> = _stateSale
    fun postSale(token: String, body: JsonObject) {
        _stateSale.value = UIStateResponse.Loading
        viewModelScope.launch {
            val resp = repo.postSale(token, body)
            if (resp.isSuccess) {
                _stateSale.value = UIStateResponse.Success(resp.getOrNull())
            } else {
                _stateSale.value = UIStateResponse.Error(resp.exceptionOrNull()?.message ?: "")
            }
        }
    }

    // Data State for our collectors post settlement
    private val _stateSettlement = MutableStateFlow<UIStateResponse<SettlementModel?>>(UIStateResponse.Loading)
    val stateSettlement: StateFlow<UIStateResponse<SettlementModel?>> = _stateSettlement
    fun postSettlement(token: String) {
        _stateSettlement.value = UIStateResponse.Loading
        viewModelScope.launch {
            val resp = repo.postSettlement(token)
            if (resp.isSuccess) {
                _stateSettlement.value = UIStateResponse.Success(resp.getOrNull())
            } else {
                _stateSettlement.value = UIStateResponse.Error(resp.exceptionOrNull()?.message ?: "")
            }
        }
    }

    // we need to clearState for handle infinity recompose
    fun clearStateSale() {
        _stateSale.value = UIStateResponse.Idle // atau default lain
    }
}