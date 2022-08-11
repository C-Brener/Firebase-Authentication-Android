package br.com.alura.aluraesporte.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.ResourceValue
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

private const val TAG = "FirebaseAuthRepository"

class RegisterUserViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {

    private val _registrationResourceLiveData = MutableLiveData<ResourceValue>()
    val registrationResourceLiveData: LiveData<ResourceValue> = _registrationResourceLiveData

    fun cadastra(user: User) {
        val registerUser = repository.registerUser(
            user
        )
        handleStateRegisterUser(registerUser)
    }

    private fun handleStateRegisterUser(taskResultCreateUser: Task<AuthResult>) {
        taskResultCreateUser.addOnSuccessListener { AuthResultSuccess ->
            Log.i(TAG, "Usuario cadastrado com sucesso")
            _registrationResourceLiveData.postValue(ResourceValue(data = true, ""))
        }
        taskResultCreateUser.addOnFailureListener { AuthResultFailure ->
            val captureMessageException = captureExceptionFirebase(AuthResultFailure)
            _registrationResourceLiveData.postValue(
                ResourceValue(
                    data = false,
                    captureMessageException
                )
            )
        }

    }

    private fun captureExceptionFirebase(AuthResultFailure: Exception): String {
        val exception: String = when (AuthResultFailure) {
            is FirebaseAuthWeakPasswordException -> "A senha precisa de no minimo 6 digitos"
            is FirebaseAuthInvalidCredentialsException -> "O e-mail não atende os padrões corretos"
            is FirebaseAuthUserCollisionException -> "O e-mail cadastrado já esta sendo utilizado"
            else -> "Erro Desconhecido"
        }
        return exception
    }
}
