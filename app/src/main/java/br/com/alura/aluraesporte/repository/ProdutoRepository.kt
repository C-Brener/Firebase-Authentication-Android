package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.mapper.ProductDocumentFireStore
import br.com.alura.aluraesporte.model.Produto
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

private const val collectionFireStore = "produtos"

class ProdutoRepository(private val firestore: FirebaseFirestore) {

    fun buscaPorId(id: String): LiveData<Produto> = MutableLiveData<Produto>().apply {
        firestore.collection(collectionFireStore).document(id)
            .addSnapshotListener { s, _ ->
                s?.let { documentSnapshot ->
                    documentSnapshot.toObject<ProductDocumentFireStore>()
                        ?.mapperForProductModel(documentSnapshot.id)
                        ?.let { produto ->
                            value = produto
                        }
                }
            }
    }

    fun savedInDatabaseInFirestore(produto: Produto): DocumentReference {
        val produtoDocumento =
            ProductDocumentFireStore(nome = produto.nome, preço = produto.preco.toString())
        val colecao = firestore.collection(collectionFireStore)
        val documento = produto.id?.let {
            colecao.document(it)
        } ?: colecao.document()

        documento.set(produtoDocumento)
        Log.i("Gerando ID localmente", documento.id)

        return documento
    }

    fun searchDataInFirestore(): CollectionReference {
        return firestore.collection(collectionFireStore)
    }

    fun remove(produtoId: String): LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        firestore.collection(collectionFireStore)
            .document(produtoId)
            .delete()
        value = true
    }

}
