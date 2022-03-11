package br.com.calculaflex.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.LoginUseCase
import br.com.calculaflex.domain.usecases.ResetPasswordUseCase

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            LoginUseCase::class.java,
            ResetPasswordUseCase::class.java
        ).newInstance(loginUseCase, resetPasswordUseCase)
    }
}
