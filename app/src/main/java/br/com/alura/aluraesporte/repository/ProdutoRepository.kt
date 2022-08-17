package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Produto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.math.BigDecimal

class ProdutoRepository(private val dao: ProdutoDAO, private val firestore: FirebaseFirestore) {

    fun buscaTodos(): LiveData<List<Produto>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Produto> = dao.buscaPorId(id)

    fun savedInDatabaseInFirestore() {
        val produto = Produto(nome = "Xbox Series", preco = BigDecimal(2000.99))
        val productMapped: Map<String, Any> = mapOf<String, Any>(
            "nome" to produto.nome,
            "preco" to produto.preco.toDouble()
        )
        firestore.collection("produtos")
            .add(productMapped)
            .addOnSuccessListener {
                it?.let {
                    Log.i("Inserindo dados", it.id)
                }
            }
    }

    fun searchDatabaseInFirestore(): Task<QuerySnapshot> {
        return firestore.collection("produtos")
            .get()
    }
}