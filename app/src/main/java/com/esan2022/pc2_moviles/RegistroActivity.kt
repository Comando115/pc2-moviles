package com.esan2022.pc2_moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val textViewRegistroDNI: EditText = findViewById(R.id.textViewRegistroDNI)
        val textViewRegistroName: EditText = findViewById(R.id.textViewRegistroName)
        val textViewRegistroPass: EditText = findViewById(R.id.textViewRegistroPass)
        val textViewRegistroPassRepeat: EditText = findViewById(R.id.textViewRegistroPassRepeat)

        val buttonRRegister: Button = findViewById(R.id.buttonRRegister)
        buttonRRegister.setOnClickListener {
            if (textViewRegistroPass.text.toString().equals(textViewRegistroPassRepeat.text.toString())) {
                val miusuario = UsuarioModel(
                    textViewRegistroDNI.text.toString(),
                    textViewRegistroName.text.toString(),
                    textViewRegistroPass.text.toString()
                )
                val db = Firebase.firestore
                db.collection("usuarios")
                    .whereEqualTo("dni", miusuario.dni)
                    .get()
                    .addOnSuccessListener { documents ->
                        var totalDocumentos = 0
                        for (document in documents) {
                            totalDocumentos = totalDocumentos + 1
                        }
                        if (totalDocumentos > 0) {
                            val mensaje = "Ya existe el usuario : "  + miusuario.dni
                            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                        } else {
                            db.collection("usuarios").add(miusuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registrado.", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Ocurrió un error.", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Error getting documents", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Las contraseñas tienen que ser iguales.", Toast.LENGTH_LONG).show()
            }
        }
    }
}