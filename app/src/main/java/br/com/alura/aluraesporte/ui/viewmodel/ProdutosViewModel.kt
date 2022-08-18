package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

class ProdutosViewModel(private val repository: ProdutoRepository) :
    ViewModel() {

    private val _listProduct: MutableLiveData<List<Produto>> = MutableLiveData<List<Produto>>()
    val listProduct: LiveData<List<Produto>> = _listProduct


    fun searchDataInFirebase() {
        getResultsSearchData(repository.searchDatabaseInFirestore())
    }

    private fun getResultsSearchData(query: Task<QuerySnapshot>) {
        val list: MutableList<Produto> = mutableListOf()
        query.addOnSuccessListener {
            it.let { querySnapshot ->
                for (document in querySnapshot) {
                    val produto = document.toObject<ProductDocumentFireStore>()
                    produto.let { productDocumentNotNull ->
                        list.add(
                            productDocumentNotNull.mapperForProductModel()
                        )
                    }
                    }
                }
                _listProduct.postValue(list)
            }
        }
    }

    private class ProductDocumentFireStore(
        val nome: String = "",
        val preço: String = "0.0"
    ) {
        fun mapperForProductModel(): Produto = Produto(
            nome = nome,
            preco = BigDecimal(preço)
        )
    }


