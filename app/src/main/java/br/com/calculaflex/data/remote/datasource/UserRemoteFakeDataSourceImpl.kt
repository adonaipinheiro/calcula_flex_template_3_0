package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        delay(2000)
        return RequestState.Success(User("Heider"))
        //return RequestState.Error(Exception("Usuário expirado"))
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return if(userLogin.email == "heider" && userLogin.password == "123456") {
            RequestState.Success(User("Heider"))
        } else {
            RequestState.Error(Exception("Usuario ou senha inválida"))
        }
    }

}
