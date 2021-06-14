package com.example.hw9android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val emailPattern: Regex = Regex("^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+\$")
    val passwordPattern: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,100}\$")

    private lateinit var emailField: EditText
    private lateinit var pas1Field: EditText
    private lateinit var pas2Field: EditText
    private lateinit var signup: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailField = findViewById(R.id.email)
        pas1Field = findViewById(R.id.pas1)
        pas2Field = findViewById(R.id.pas2)
        signup = findViewById(R.id.signup)
        progressBar = findViewById(R.id.progressBar)


        signup.setOnClickListener {

            val email = emailField.text.toString()
            val pas1 = pas1Field.text.toString()
            val pas2 = pas2Field.text.toString()
            var errors = 0

            if(!email.matches(emailPattern)){
                emailField.setError("invalid email")
                errors++
            }
            if(!pas1.matches(passwordPattern)){
                pas1Field.setError("password must be at least 8 characters long and contain at least one upper case letter, one lower case letter and one number")
                errors++
            }
            if(pas1 != pas2){
                pas2Field.setError("passwords don't match")
                errors++
            }


            if(errors==0){

                loading()

                mAuth.createUserWithEmailAndPassword(email, pas1)
                    .addOnCompleteListener { createAccount->
                        if(createAccount.isSuccessful){
                            mAuth.signInWithEmailAndPassword(email, pas1)
                                .addOnCompleteListener{ login->
                                    if(login.isSuccessful){
                                        startActivity(Intent(this, AccountActivity::class.java))
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this,"could not log in",Toast.LENGTH_SHORT).show()
                                        loaded()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(this,"account could not be created",Toast.LENGTH_SHORT).show()
                            loaded()
                        }
                    }
            }

        }

    }

    fun loading(){
        emailField.visibility = View.INVISIBLE
        pas1Field.visibility = View.INVISIBLE
        pas2Field.visibility = View.INVISIBLE
        signup.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun loaded(){
        emailField.visibility = View.VISIBLE
        pas1Field.visibility = View.VISIBLE
        pas2Field.visibility = View.VISIBLE
        signup.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}