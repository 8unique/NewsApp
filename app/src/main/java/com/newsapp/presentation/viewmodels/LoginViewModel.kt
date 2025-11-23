package com.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.domain.usecase.LoginUseCase
import com.newsapp.domain.usecase.SignUpUseCase
import com.newsapp.presentation.screens.uistates.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            try {
                val result = loginUseCase(email, password)
                if (result.isSuccess) {
                    _loginUiState.value = LoginUiState.Success
                } else {
                    _loginUiState.value = LoginUiState.Error(
                        result.exceptionOrNull()?.message ?: "Login failed"
                    )
                }
            } catch (e: Exception) {
                _loginUiState.value = LoginUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            try {
                val result = signUpUseCase(firstName, lastName, email, password)
                if (result.isSuccess) {
                    _loginUiState.value = LoginUiState.Success
                } else {
                    _loginUiState.value = LoginUiState.Error(
                        result.exceptionOrNull()?.message ?: "Sign up failed"
                    )
                }
            } catch (e: Exception) {
                _loginUiState.value = LoginUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _loginUiState.value = LoginUiState.Idle
    }
}