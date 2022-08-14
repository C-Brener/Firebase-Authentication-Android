package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private val controlador by lazy {
        findNavController(R.id.main_activity_nav_host)
    }
    private val viewModel: EstadoAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val firestore = Firebase.firestore
        firestore.collection("produtos")
            .get()
            .addOnSuccessListener {
                callInfosInDatabase(it)
            }
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
        controlador.addOnDestinationChangedListener { _,
                                                      destination,
                                                      _ ->
            title = destination.label
            viewModel.componentes.observe(this) {
                it?.let { temComponentes ->
                    if (temComponentes.appBar) {
                        supportActionBar?.show()
                    } else {
                        supportActionBar?.hide()
                    }
                    if (temComponentes.bottomNavigation) {
                        main_activity_bottom_navigation.visibility = VISIBLE
                    } else {
                        main_activity_bottom_navigation.visibility = GONE
                    }
                }
            }
        }
        main_activity_bottom_navigation
            .setupWithNavController(controlador)
    }

    private fun callInfosInDatabase(it: QuerySnapshot) {
        it.let { querySnapshot ->
            for (document in querySnapshot) {
                Log.i("Teste FireStore", "FireStore Testado com esses produtos:${document.data}")
            }

        }
    }


}
