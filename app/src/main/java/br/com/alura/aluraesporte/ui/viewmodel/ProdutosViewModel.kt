package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.mapper.ProductDocumentFireStore
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.ProdutoRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject

class ProdutosViewModel(private val repository: ProdutoRepository) :
    ViewModel() {

    private val _listProduct: MutableLiveData<List<Produto>> = MutableLiveData<List<Produto>>()
    val listProduct: LiveData<List<Produto>> = _listProduct

    fun searchDataInFirebase() {
        handleResultsSearchData(repository.searchDataInFirestore())
    }

    private fun handleResultsSearchData(query: CollectionReference) {
        val list: MutableList<Produto> = mutableListOf()
        query.addSnapshotListener { value, error ->
            value?.let { querySnapshot ->
                for (document in querySnapshot) {
                    val produto = document.toObject<ProductDocumentFireStore>()
                    produto.let { productDocumentNotNull ->
                        list.add(
                            productDocumentNotNull.mapperForProductModel(document.id)
                        )
                    }
                }
                _listProduct.postValue(list)
            }
        }
    }

}




