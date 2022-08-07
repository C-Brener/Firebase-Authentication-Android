package br.com.alura.aluraesporte.repository

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthRepository {


    private fun logOff(auth: FirebaseAuth) {
        auth.signOut()
    }

    private fun verifyUser(auth: FirebaseAuth) {
        auth.currentUser.let {
        }
    }

    private fun authUser(auth: FirebaseAuth, email: String, password: String): Unit {
        val signInWithEmailAndPassword = auth.signInWithEmailAndPassword(email, password)
        signInWithEmailAndPassword.addOnSuccessListener {

        }
        return Unit
    }

    private fun registerUser(auth: FirebaseAuth) {
        val taskResultCreateUser =
            auth.createUserWithEmailAndPassword("caique.brener@testeauth.com", "12345678")
        taskResultCreateUser.addOnSuccessListener { AuthResultSuccess ->
        }
        taskResultCreateUser.addOnFailureListener { AuthResultFailure ->
        }
    }
}