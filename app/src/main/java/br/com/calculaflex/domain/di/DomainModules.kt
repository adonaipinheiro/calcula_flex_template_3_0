package br.com.calculaflex.domain.di

import br.com.calculaflex.domain.usecases.*
import br.com.calculaflex.domain.utils.FuelCalculator
import org.koin.dsl.module

val domainModules = module {

    factory { FuelCalculator() }

    factory { CalculateBetterFuelUseCase(fuelCalculator = get()) }
    factory { CreateUserUseCase(userRepository = get()) }
    factory { GetCarUseCase(carRepository = get()) }
    factory { GetDashboardMenuUseCase(appRespository = get()) }
    factory { GetMinAppVersionUseCase(appRespository = get()) }
    factory { GetUserLoggedUseCase(userRepository = get()) }
    factory { LoginUseCase(userRepository = get()) }
    factory { ResetPasswordUseCase(userRepository = get()) }
    factory { SaveCarUseCase(getUserLoggedUseCase = get(), carRepository = get()) }
    factory { SignOutUseCase(userRepository = get()) }

}
