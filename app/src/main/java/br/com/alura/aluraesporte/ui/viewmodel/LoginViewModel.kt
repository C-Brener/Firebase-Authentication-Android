package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun loga(email: String, password: String) {
        firebaseAuthRepository.authUser(email, password)
    }

    fun desloga() {
        firebaseAuthRepository.logOff()
    }

    fun estaLogado(): Boolean = firebaseAuthRepository.verifyUser()

    fun naoEstaLogado(): Boolean = !estaLogado()

}
