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
        val verification = auth.currentUser
        if (verification != null) {
            return true
        }
        return false
    }

    fun authUser(user: User): Task<AuthResult> {
        return user.run {
            auth.signInWithEmailAndPassword(this.email, this.password)
        }
    }

    fun registerUser(user: User): Task<AuthResult> {
        return user.run {
            auth.createUserWithEmailAndPassword(this.email, this.password)
        }
    }

    fun captureInfoUser():String{
        val user = auth.currentUser?.email
        user?.let {
            return it
        }
        return "Usuário não encontrado"
    }


}