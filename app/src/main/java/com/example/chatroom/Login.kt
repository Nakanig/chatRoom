package com.example.chatroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignup)


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            
            if(email.isEmpty()){
                edtEmail.error = "Aucilebelia velis shevseba"
                return@setOnClickListener
            }else if (password.isEmpty()){
                edtPassword.error = "Aucilebelia velis shevseba"
                return@setOnClickListener
            }else{
                Toast.makeText(this, "LOADING...", Toast.LENGTH_SHORT).show()
            }

            login(email, password)
        }


    }

    private fun login(email: String, password: String){
        // log in logika useristvis

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code login rom gaiaro userit

                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }else{
                    Toast.makeText(this@Login, "User ar aris napovni", Toast.LENGTH_SHORT).show()
                }
            }
    }
    val firebaseAuth = FirebaseAuth.getInstance()


    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth!!.addAuthStateListener(this.authStateListener!!)
    }


}