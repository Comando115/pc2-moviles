package com.esan2022.pc2_moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewLoginDNI: EditText = findViewById(R.id.textViewLoginDNI)
        val textViewLoginContra: EditText = findViewById(R.id.textViewLoginContra)

        val buttonRegistro: Button = findViewById(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        val buttonIniciarSesion: Button = findViewById(R.id.buttonIniciarSesion)
        buttonIniciarSesion.setOnClickListener {
            val db = Firebase.firestore
            db.collection("usuarios")
                .whereEqualTo("dni", textViewLoginDNI.text.toString())
                .whereEqualTo("contra", textViewLoginContra.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    var totalDocumentos = 0
                    for (document in documents) {
                        totalDocumentos = totalDocumentos + 1
                    }
                    if (totalDocumentos == 1) {
                        Toast.makeText(this, "ACCESO PERMITIDO", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Contraseña ó Usuario incorrecto.", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting documents", Toast.LENGTH_LONG).show()
                }
        }
    }
}