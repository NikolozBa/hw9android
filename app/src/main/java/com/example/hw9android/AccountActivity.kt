package com.example.hw9android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var accountText: TextView
    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        accountText = findViewById(R.id.accountText)
        logout = findViewById(R.id.logout)

        accountText.text = "Congratulations,\nyour account was successfully created.\nYour e-mail is:\n" + mAuth.currentUser?.email

        logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}