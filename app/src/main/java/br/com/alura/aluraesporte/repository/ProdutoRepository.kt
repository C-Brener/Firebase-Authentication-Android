package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.mapper.ProductDocumentFireStore
import br.com.alura.aluraesporte.model.Produto
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

private const val collectionFireStore = "produtos"

class ProdutoRepository(private val firestore: FirebaseFirestore) {

    fun buscaPorId(id: Long): LiveData<Produto> =
        TODO("Não foi implementada a busca do produto por ID")

    fun savedInDatabaseInFirestore(produto: Produto): DocumentReference {
        val produtoDocumento =
            ProductDocumentFireStore(nome = produto.nome, preço = produto.preco.toString())
        val documento = firestore.collection(collectionFireStore)
            .document()
        documento.set(produtoDocumento)
        Log.i("Gerando ID localmente", documento.id)

        return documento
    }

    fun searchDataInFirestore(): CollectionReference {
        return firestore.collection(collectionFireStore)
    }

}
