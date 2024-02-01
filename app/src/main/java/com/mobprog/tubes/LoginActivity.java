package com.mobprog.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected Button button1;
    protected EditText editText1;
    protected EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v -> {
            String inName = editText1.getText().toString();
            String inPass = editText2.getText().toString();

            String name = Pengaturan.ambilUserName(getApplicationContext());
            String pass = Pengaturan.ambilUserPass(getApplicationContext());

            if (name.equals(inName) && pass.equals(inPass)) {
                Util.showMessage(this, "Login berhasil!");
                finish();
            } else {
                Util.showDialog(this, "Login Gagal!");
            }
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
