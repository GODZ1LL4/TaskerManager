package com.example.taskermanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.taskermanager.databinding.ActivityUserInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)


        auth = Firebase.auth

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val db = Firebase.firestore

        db.collection("users").document(uid).get().addOnSuccessListener {
            binding.eTxtNombreUser.text = ("Nombre: " + it.get("nombre") as String?)
            binding.eTxtApellidoUser.text = ("Apellido: " + it.get("apellido") as String?)
            binding.eTxtCorreoUser.text = ("Correo: " + it.get("correo") as String?)
            binding.eTxtTelefonoUser.text = ("Telefono: " + it.get("telefono") as String?)
            binding.eTxtGeneroUser.text = ("Genero: " + it.get("genero") as String?)
            binding.eTxtfechaUser.text = ("Fecha de nacimiento: " + it.get("fechanacimiento") as String?)
            binding.eTxtPaisUser.text = ("Pais: " + it.get("pais") as String?)
            binding.eTxtProvinciaUser.text = ("Provincia: " + it.get("provincia") as String?)
            binding.eTxtDireccionUser.text = ("Direccion: " + it.get("direccion") as String?)

        }

        binding.btnLogout.setOnClickListener {
            cerrarSesion()
        }

    }

    private fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(
            intent
        )
    }


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        } else {

        }
    }

    private fun reload() {

    }

}


