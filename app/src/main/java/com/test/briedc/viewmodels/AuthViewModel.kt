package com.test.briedc.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.test.briedc.data.models.LoginModel
import com.test.briedc.data.models.RegisterModel
import com.test.briedc.data.models.base.UIStateResponse
import com.test.briedc.data.remote.RetrofitClient
import com.test.briedc.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repo = AuthRepository(RetrofitClient.apiService)

    // Data State for our collectors post login
    private val _stateLogin = MutableStateFlow<UIStateResponse<LoginModel?>>(UIStateResponse.Loading)
    val stateLogin: StateFlow<UIStateResponse<LoginModel?>> = _stateLogin
    fun login(body: JsonObject) {
        _stateLogin.value = UIStateResponse.Loading
        viewModelScope.launch {
            val resp = repo.login(body)
            if (resp.isSuccess) {
                _stateLogin.value = UIStateResponse.Success(resp.getOrNull())
            } else {
                _stateLogin.value = UIStateResponse.Error(resp.exceptionOrNull()?.message ?: "")
            }
        }
    }

    // Data State for our collectors post login
    private val _stateRegister = MutableStateFlow<UIStateResponse<RegisterModel?>>(UIStateResponse.Loading)
    val stateRegister: StateFlow<UIStateResponse<RegisterModel?>> = _stateRegister
    fun register(body: JsonObject) {
        _stateRegister.value = UIStateResponse.Loading
        viewModelScope.launch {
            val resp = repo.register(body)
            if (resp.isSuccess) {
                _stateRegister.value = UIStateResponse.Success(resp.getOrNull())
            } else {
                _stateRegister.value = UIStateResponse.Error(resp.exceptionOrNull()?.message ?: "")
            }
        }
    }
}