package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.Resource

class CadastroUsuarioViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {


    fun cadastra(email: String, password: String):LiveData<Resource<Boolean>>{
        return repository.registerUser(email=email, password = password)
    }
}