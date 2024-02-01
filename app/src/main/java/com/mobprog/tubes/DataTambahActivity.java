package com.mobprog.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataTambahActivity extends AppCompatActivity {

    protected Button button1;
    protected EditText editText1;
    protected EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tambah);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(this::button1_OnClick);
    }

    protected void button1_OnClick(View v) {

    }
}
