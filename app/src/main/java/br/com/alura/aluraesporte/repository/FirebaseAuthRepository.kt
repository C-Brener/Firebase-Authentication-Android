package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.ResourceValue
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*


class FirebaseAuthRepository(private val auth: FirebaseAuth) {
    private lateinit var resourceValue: ResourceValue

    private fun logOff(auth: FirebaseAuth) {
        auth.signOut()
    }

    private fun verifyUser(auth: FirebaseAuth) {
        auth.currentUser.let {
        }
    }

    private fun authUser(auth: FirebaseAuth, email: String, password: String) {
        val signInWithEmailAndPassword = auth.signInWithEmailAndPassword(email, password)
        signInWithEmailAndPassword.addOnSuccessListener {

        }
        return Unit
    }

    fun registerUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)

    }


}