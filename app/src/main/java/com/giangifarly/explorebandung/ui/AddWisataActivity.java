package com.giangifarly.explorebandung.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giangifarly.explorebandung.MainActivity;
import com.giangifarly.explorebandung.R;
import com.giangifarly.explorebandung.model.ModelWisata;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWisataActivity extends AppCompatActivity {
    EditText lokasi, alamat;
    Button btn_simpan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wisata);

        lokasi = findViewById(R.id.inputLokasi);
        alamat = findViewById(R.id.inputAlamat);
        btn_simpan = findViewById(R.id.btnSave);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNama = lokasi.getText().toString();
                String getDeskripsi = alamat.getText().toString();

                if (getNama.isEmpty()){
                    lokasi.setError("Nama tempat wisata harus di isi !");
                }else if (getDeskripsi.isEmpty()){
                    alamat.setError("Deskripsi tempat wisata harus di isi!");
                }else{
                    database.child("Wisata").push().setValue(new ModelWisata(getNama, getDeskripsi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddWisataActivity.this, "Data berhasil di simpan !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddWisataActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddWisataActivity.this, "Gagal menyimpan data !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }
}