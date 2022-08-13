package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository

class MinhaContaViewModel(private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> = _email

    init {
        getUser()
    }

    private fun getUser() {
        _email.postValue(authRepository.captureInfoUser())
    }
}