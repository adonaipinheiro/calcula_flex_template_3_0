package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.UserRepository

class ResetPasswordUseCase(
    private val userRepository: UserRepository
) {

    suspend fun resetPassword(email: String): RequestState<String> =
        userRepository.resetPassword(email)
}
