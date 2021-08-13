package com.giangifarly.explorebandung.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.giangifarly.explorebandung.R
import com.giangifarly.explorebandung.model.LokasiWisata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddLocationActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var namalokasi: EditText
    lateinit var alamat : EditText
    lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        namalokasi = findViewById(R.id.inputNamaLokasi)
        alamat = findViewById(R.id.inputAlamat)
        btnSave = findViewById(R.id.btnSaveLocation)

        btnSave.setOnClickListener{
            saveData()
        }
    }


    private fun saveData(){
        val namaLokasi = namalokasi.text.toString().trim()
        val Alamat = alamat.text.toString().trim()

        if (namaLokasi.isEmpty()){
            namalokasi.error = "Isi Nama Lokasi Wisata"
            return
        }
        if (Alamat.isEmpty()){
            alamat.error = "Isi Alamat"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("lokasi_wisata")

        val lokasiId = ref.push().key!!

        val lks = LokasiWisata(lokasiId,namaLokasi, Alamat)

        if (lokasiId != null){
            ref.child(lokasiId).setValue(lks).addOnCompleteListener {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            }
        }

    }


}