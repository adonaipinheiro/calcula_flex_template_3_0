package br.com.calculaflex.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.usecases.LoginUseCase
import br.com.calculaflex.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()

    val resetPasswordState = MutableLiveData<RequestState<String>>()

    fun resetPassword(email: String) {
        viewModelScope.launch {
            resetPasswordState.value = resetPasswordUseCase.resetPassword(email)
        }
    }


    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }
}
