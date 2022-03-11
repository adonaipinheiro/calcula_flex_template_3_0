package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class UserRemoteFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        FirebaseAuth.getInstance().currentUser?.reload()
        val firebaseUser = firebaseAuth.currentUser
        return if (firebaseUser == null) {
            RequestState.Error(Exception("Usuário deslogado"))
        } else {
            RequestState.Success(User(firebaseUser.displayName ?: "Não identificado"))
        }
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(userLogin.email, userLogin.password).await()
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser?.email != null) {
                RequestState.Success(User(firebaseUser.displayName ?: "Não identificado"))
            } else {
                RequestState.Error(Exception("Usuario ou senha inválida"))
            }
        } catch (excpetion: java.lang.Exception) {
            RequestState.Error(excpetion)
        }
    }
}
