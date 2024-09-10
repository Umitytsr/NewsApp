package com.umitytsr.myapplication.data.repo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.umitytsr.myapplication.ui.authentication.MainActivity
import com.umitytsr.myapplication.ui.news.NewsActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) {

    fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(context, NewsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }
        }
    }

    fun signUp(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            Toast.makeText(context, "The Password Does Not Match", Toast.LENGTH_SHORT).show()
        } else {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(context, NewsActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Please Fill in All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signOut(requireActivity: Activity) {
        auth.signOut()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        requireActivity.finish()
    }

}