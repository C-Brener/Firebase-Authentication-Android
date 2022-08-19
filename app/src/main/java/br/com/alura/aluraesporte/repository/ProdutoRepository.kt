package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.mapper.ProductDocumentFireStore
import br.com.alura.aluraesporte.model.Produto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

class ProdutoRepository(private val dao: ProdutoDAO, private val firestore: FirebaseFirestore) {

    fun buscaTodos(): LiveData<List<Produto>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Produto> = dao.buscaPorId(id)

    fun savedInDatabaseInFirestore(produto:Produto) : Task<DocumentReference> {
        val produtoDocumento = ProductDocumentFireStore(nome = produto.nome, preço = produto.preco.toString())
       return firestore.collection("produtos")
            .add(produtoDocumento)

    }

    fun searchDataInFirestore(): CollectionReference {
        return firestore.collection("produtos")
    }
}