package com.mobprog.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends AppCompatActivity {

    protected Button button1;
    protected EditText editText1;
    protected EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v -> {
            String name = editText1.getText().toString();
            String pass = editText2.getText().toString();

            Pengaturan.simpanUserName(getApplicationContext(), name);
            Pengaturan.simpanUserPass(getApplicationContext(), pass);

            Toast.makeText(this, "User ditambahkan!", Toast.LENGTH_SHORT).show();
            finish();
        });
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
