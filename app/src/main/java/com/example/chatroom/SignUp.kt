package com.example.chatroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtConfirmPassword = findViewById(R.id.edt_confirmpassword)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val confirmpassword = edtConfirmPassword.text.toString()
            
            if (name.isEmpty()){
                edtName.error = "Aucilebelia velis shevseba"
                return@setOnClickListener
            }else if (email.isEmpty() && !email.contains("@")){
                edtEmail.error = "Aucilebelia  velis validuri email-it shevseba"
                return@setOnClickListener
            }else if (password.isEmpty()) {
                edtPassword.error = "Aucilebelia velis shevseba"
                return@setOnClickListener
            }else if (password.length <= (9)) {
                Toast.makeText(this, "Paroli unda sheicavdes 9 simbolos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (!password.contains("1") and !password.contains("2") and !password.contains("3")
                and !password.contains("4") and !password.contains("5") and !password.contains("6")
                and !password.contains("7") and !password.contains("8") and !password.contains("9")
                and !password.contains("0")){
                Toast.makeText(this, "Paroli unda sheicavdes min 1 cifrs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (confirmpassword.isEmpty()) {
                edtConfirmPassword.error = "Aucilebelia velis shevseba"
                return@setOnClickListener
            }else if (confirmpassword != password) {
                Toast.makeText(this, "Aucilebelia parolebi emtxveodes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "LOADING...", Toast.LENGTH_SHORT).show()
            }
            

            signup(name,email,password,confirmpassword)
        }

    }

    private fun signup(name:String, email: String, password: String, confirmpassword: String){
        // user sheqmnis logika

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code rom gadavidet homeze
                        addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)


                }else {
                    Toast.makeText(this@SignUp, "Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }


}