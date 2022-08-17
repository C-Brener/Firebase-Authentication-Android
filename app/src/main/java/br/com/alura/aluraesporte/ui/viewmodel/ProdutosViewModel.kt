package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import java.math.BigDecimal

class ProdutosViewModel(private val repository: ProdutoRepository) : ViewModel() {

    private val _listProduct: MutableLiveData<List<Produto>> = MutableLiveData<List<Produto>>()
    val listProduct: LiveData<List<Produto>> = _listProduct


    fun buscaTodosNoFirebase() {
        getResults(repository.searchDatabaseInFirestore())
    }

    fun getResults(query: Task<QuerySnapshot>) {
        val list: MutableList<Produto> = mutableListOf()
        query.addOnSuccessListener {
            it.let { querySnapshot ->
                for (document in querySnapshot) {
                    document.data.let { dados ->
                        var list1: MutableList<Produto> = mutableListOf()
                        val nome: String = dados["nome"] as String
                        val preco: Double? = dados["preço"] as? Double
                        val produto: Produto = Produto(nome = nome, preco = BigDecimal(1))
                        list.add(produto)
                    }
                }
            }
            _listProduct.postValue(list)
        }
    }

}
