package com.giangifarly.explorebandung.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.giangifarly.explorebandung.MainActivity
import com.giangifarly.explorebandung.R
import com.giangifarly.explorebandung.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray:Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        signInInputsArray = arrayOf(email,password)
        btnLogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser(){
        signInEmail = email.toString().trim()
        signInPassword = password.toString().trim()

        if (notEmpty()){
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword).addOnCompleteListener{ signIn ->
                if (signIn.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()){
                    input.error = "${input.hint} is required"
                }
            }
        }
    }

    private fun signIn(){
        val email = email.text.toString()
        val password = password.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please insert email and password", Toast.LENGTH_SHORT).show()
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful){ return@addOnCompleteListener
                    val intent = Intent(this, MainActivity::class.java)
                }else {
                    Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
                Log.d("Main", "Failed Login : ${it.message}")
                Toast.makeText(this, "Email/Password incorrect", Toast.LENGTH_SHORT).show()
            }
    }
}