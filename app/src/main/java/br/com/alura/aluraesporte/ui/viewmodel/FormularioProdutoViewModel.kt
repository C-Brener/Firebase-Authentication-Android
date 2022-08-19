package br.com.alura.aluraesporte.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

class FormularioProdutoViewModel(private val firestoreDataBase: ProdutoRepository) : ViewModel() {

    private val _getTaskStatus: MutableLiveData<Boolean> = MutableLiveData()
     val getTaskStatus: LiveData<Boolean> = _getTaskStatus

    fun salva(produto: Produto) {
        captureTask(firestoreDataBase.savedInDatabaseInFirestore(produto))

    }

    private fun captureTask(task: Task<DocumentReference>) {
        task.addOnSuccessListener {
            it?.let {
                Log.i("Inserindo dados", it.id)
                _getTaskStatus.postValue(true)
            }
        }
        task.addOnFailureListener {
            _getTaskStatus.postValue(false)
        }
    }
}


