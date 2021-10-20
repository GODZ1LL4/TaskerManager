package com.example.taskermanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.taskermanager.databinding.ActivityRecoveryPassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoveryPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recovery_pass)

        binding.btnEnviar.setOnClickListener {
            val emailAddress = binding.xTxtRecoveryEmail.text.toString()

            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show()
                        backHome()
                    }
                }
        }
    }

    private fun backHome() {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }
}