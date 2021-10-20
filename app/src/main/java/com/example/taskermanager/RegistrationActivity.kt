package com.example.taskermanager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.taskermanager.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    lateinit var datePickerDialog: DatePickerDialog


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)



        binding.eTxtFechaRegistro.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)

            val mMonth: Int = c.get(Calendar.MONTH)

            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)

            datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.eTxtFechaRegistro.setText(
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }

        binding.btnEnviarRegistro.setOnClickListener {
            val nombre = binding.eTxtNombreRegistro.text.toString()
            val apellido = binding.eTxtApellidoRegistro.text.toString()
            val telefono = binding.eTxtTelefonoRegistro.text.toString()
            val genero = binding.eTxtGeneroRegistro.text.toString()
            val fechanac = binding.eTxtFechaRegistro.text.toString()
            val pais = binding.eTxtPaisRegistro.text.toString()
            val provincia = binding.eTxtProvinciaRegistro.text.toString()
            val direccion = binding.eTxtDireccionRegistro.text.toString()
            val email = binding.eTxtCorreoRegistro.text.toString()
            val password = binding.eTxtPasswordRegistro.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && genero.isNotEmpty() && fechanac.isNotEmpty() && pais.isNotEmpty() && provincia.isNotEmpty() && direccion.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registrarUsuario(
                    nombre,
                    apellido,
                    telefono,
                    genero,
                    fechanac,
                    pais,
                    provincia,
                    direccion,
                    email,
                    password
                )
            } else {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        } else {

        }
    }


    private fun registrarUsuario(
        nombre: String,
        apellido: String,
        telefono: String,
        genero: String,
        fechanac: String,
        pais: String,
        provincia: String,
        direccion: String,
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    val user = auth.currentUser

                    val uid = user!!.uid

                    val map = hashMapOf(
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "telefono" to telefono,
                        "genero" to genero,
                        "fechanacimiento" to fechanac,
                        "pais" to pais,
                        "provincia" to provincia,
                        "direccion" to direccion,
                        "correo" to email
                    )

                    val db = Firebase.firestore

                    db.collection("users").document(uid).set(map).addOnSuccessListener {
                        infoUser()
                        Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Fallo al guardar la informacion",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {


                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }


    }

    private fun infoUser() {
        val infoUserIntent = Intent(this, UserInfoActivity::class.java)
        startActivity(infoUserIntent)

    }


    private fun reload() {

    }
}