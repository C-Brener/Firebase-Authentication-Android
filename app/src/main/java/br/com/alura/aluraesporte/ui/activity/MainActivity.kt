package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val controlador by lazy {
        findNavController(R.id.main_activity_nav_host)
    }
    private val viewModel: EstadoAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        auth = Firebase.auth
//        registerUser(auth)
        authUser(auth, "caique.brener@testeauth.com", "12345678")
        verifyUser(auth)
        logOff(auth)

        controlador.addOnDestinationChangedListener { _,
                                                      destination,
                                                      _ ->
            title = destination.label
            viewModel.componentes.observe(this, Observer {
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
            })
        }
        main_activity_bottom_navigation
            .setupWithNavController(controlador)
    }

    private fun logOff(auth:FirebaseAuth) {
        auth.signOut()
    }

    private fun verifyUser(auth:FirebaseAuth) {
        auth.currentUser.let {
            Toast.makeText(this, "Usuário logado como: ${it?.email}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authUser(auth: FirebaseAuth, email: String, password: String): Unit {
        var toastBar: Unit = Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
        val signInWithEmailAndPassword = auth.signInWithEmailAndPassword(email, password)
        signInWithEmailAndPassword.addOnSuccessListener {
            toastBar = Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

        }
        return toastBar
    }

    private fun registerUser(auth: FirebaseAuth) {
        val taskResultCreateUser =
            auth.createUserWithEmailAndPassword("caique.brener@testeauth.com", "12345678")
        taskResultCreateUser.addOnSuccessListener { AuthResultSuccess ->
            Log.w("TAG", "$AuthResultSuccess")
        }
        taskResultCreateUser.addOnFailureListener { AuthResultFailure ->
            Log.w("TAG", "$AuthResultFailure")
        }
    }

}
