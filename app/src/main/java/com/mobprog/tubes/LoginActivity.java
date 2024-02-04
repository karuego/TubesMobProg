package com.mobprog.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected Button button1;
    protected Button button2;
    protected EditText editText1;
    protected EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this::button1_OnClick);
        button2.setOnClickListener(v -> {
            Intent pindah = new Intent(this, RegistActivity.class);
            startActivity(pindah);
        });
    }

    protected void button1_OnClick(View v) {
        String name = Pengaturan.ambilUserName(getApplicationContext());
        String pass = Pengaturan.ambilUserPass(getApplicationContext());

        if (name.isBlank()) {
            Util.showDialog(this, "Anda belum melakukan registrasi!");
            return;
        }

        String inName = editText1.getText().toString();
        String inPass = editText2.getText().toString();

        if (name.equals(inName) && pass.equals(inPass)) {
            Util.showMessage(this, "Login berhasil!");
            Intent pindah = new Intent(this, DataTambahActivity.class);
            startActivity(pindah);
            finish();
        } else {
            Util.showDialog(this, "Login Gagal!");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
