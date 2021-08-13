package com.giangifarly.explorebandung.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.becek39studio.explorebandoeng.extensions.Extensions.toast
import com.giangifarly.explorebandung.MainActivity
import com.giangifarly.explorebandung.R
import com.giangifarly.explorebandung.utils.FirebaseUtils.firebaseAuth
import com.giangifarly.explorebandung.utils.FirebaseUtils.firebaseUser
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        createAccountInputsArray = arrayOf(emailRegister,passwordRegister, confirmPasswordRegister)
        btnRegister.setOnClickListener{
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun notEmpty(): Boolean = emailRegister.toString().trim().isNotEmpty() &&
            passwordRegister.toString().trim().isNotEmpty() &&
            confirmPasswordRegister.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean{
        var identical = false
        if (notEmpty() && passwordRegister.toString().trim() == confirmPasswordRegister.toString().trim()){
            identical = true
        } else if(!notEmpty()){
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else{
            toast("Password didn't match!")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()){
            userEmail = emailRegister.toString().trim()
            userPassword = passwordRegister.toString().trim()

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener{task ->
                if (task.isSuccessful){
                    toast("Created account successfully!")
                    sendEmailVerification()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

    private fun sendEmailVerification(){
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    toast("email sent to $userEmail")
                }
            }
        }
    }
}