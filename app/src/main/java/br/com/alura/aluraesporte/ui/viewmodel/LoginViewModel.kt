package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.ResourceValue
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _authUserResult: MutableLiveData<ResourceValue> = MutableLiveData<ResourceValue>()
    val authUserResult: LiveData<ResourceValue> = _authUserResult


    fun autentica(user: User) {
        val authUser = firebaseAuthRepository.authUser(user)
        handleStateLogInUser(authUser)
    }

    fun desloga() {
        firebaseAuthRepository.logOff()
    }

    fun estaLogado(): Boolean = firebaseAuthRepository.verifyUser()

    fun naoEstaLogado(): Boolean = !estaLogado()


    private fun handleStateLogInUser(taskResultCreateUser: Task<AuthResult>) {
        taskResultCreateUser.addOnCompleteListener { AuthResult ->
            if (AuthResult.isSuccessful) {
                _authUserResult.postValue(ResourceValue(data = true, ""))
            } else {
                val error = captureError(AuthResult)
                _authUserResult.postValue(
                    ResourceValue(
                        data = false, information = error
                    )
                )
            }
        }
    }

    private fun captureError(AuthResult: Task<AuthResult>): String {
        return when (AuthResult.exception) {
            is FirebaseAuthInvalidCredentialsException, is FirebaseAuthInvalidUserException -> "E-mail ou senha incorretos"
            else -> "Erro não identificado"
        }
    }

}
