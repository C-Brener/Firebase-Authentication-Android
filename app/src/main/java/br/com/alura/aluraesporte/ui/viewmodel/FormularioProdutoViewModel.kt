package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository
import com.google.firebase.firestore.DocumentReference

class FormularioProdutoViewModel(private val firestoreDataBase: ProdutoRepository) : ViewModel() {

    private val _getTaskStatus: MutableLiveData<Boolean> = MutableLiveData()
    val getTaskStatus: LiveData<Boolean> = _getTaskStatus

    fun salva(produto: Produto) {
        captureTask(firestoreDataBase.savedInDatabaseInFirestore(produto))

    }

    private fun captureTask(task: DocumentReference) {
        _getTaskStatus.postValue(true)

        task.addSnapshotListener { value, error ->
            value?.let {
                _getTaskStatus.postValue(true)

            }
            error?.let {
                _getTaskStatus.postValue(false)
            }
        }

//        task.addOnSuccessListener {
//
//        }
//        task.addOnFailureListener {
//            _getTaskStatus.postValue(false)
//        }
    }

    fun buscaPorId(id: String):LiveData<Produto> {
        return firestoreDataBase.buscaPorId(id)
    }
}


