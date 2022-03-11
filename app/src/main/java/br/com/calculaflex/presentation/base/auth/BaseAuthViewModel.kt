package br.com.calculaflex.presentation.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {


    var userLogged = MutableLiveData<RequestState<User>>()

    fun getUserLogged(){
        viewModelScope.launch {
            userLogged.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}
