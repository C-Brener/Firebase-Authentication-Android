package br.com.alura.aluraesporte.repository

import br.com.alura.aluraesporte.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class FirebaseAuthRepository(private val auth: FirebaseAuth) {

    fun logOff() {
        auth.signOut()
    }

    fun verifyUser(): Boolean {
        var verification = auth.currentUser
        if (verification != null) {
            return true
        }
        return false
    }

    fun authUser(email: String, password: String) {
        val signInWithEmailAndPassword = auth.signInWithEmailAndPassword(email, password)
        signInWithEmailAndPassword.addOnSuccessListener {

        }
    }

    fun registerUser(user: User): Task<AuthResult> {
        return user.run {
            auth.createUserWithEmailAndPassword(this.email, this.password)
        }
    }


}