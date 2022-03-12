package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository
) {

    suspend fun signOUt(): RequestState<String> =
        userRepository.signOut()

}