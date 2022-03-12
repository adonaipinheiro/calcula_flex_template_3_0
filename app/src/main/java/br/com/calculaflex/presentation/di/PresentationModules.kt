package br.com.calculaflex.presentation.di

import br.com.calculaflex.presentation.base.BaseViewModel
import br.com.calculaflex.presentation.base.auth.BaseAuthViewModel
import br.com.calculaflex.presentation.betterfuel.BetterFuelViewModel
import br.com.calculaflex.presentation.home.HomeViewModel
import br.com.calculaflex.presentation.login.LoginViewModel
import br.com.calculaflex.presentation.signup.SignUpViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { BaseAuthViewModel(getUserLoggedUseCase = get()) }
    viewModel { BaseViewModel(getMinAppVersionUseCase = get()) }
    viewModel { BetterFuelViewModel(saveCarUseCase = get(), calculateBetterFuelUseCase = get(), getCarUseCase = get()) }
    viewModel { HomeViewModel(getDashboardMenuUseCase = get(), getUserLoggedUseCase = get()) }
    viewModel { LoginViewModel(loginUseCase = get(), resetPasswordUseCase = get()) }
    viewModel { SignUpViewModel(createUserUseCase = get()) }
}
