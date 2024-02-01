package com.mobprog.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataDetailActivity extends AppCompatActivity {

    protected Button button1;
    protected Button button2;
    protected TextView textView1;
    protected EditText editText1;
    protected EditText editText2;
    protected EditText editText3;

    protected Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        textView1 = findViewById(R.id.textView1);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this::button1_OnClick);

        button2.setOnClickListener(v -> {
            button2.setEnabled(false);
            button2.setVisibility(View.INVISIBLE);
        });
    }

    public void button1_OnClick(View v) {
        if (!isEdit) {
            isEdit = true;
            textView1.setText("Ubah Data");
            button1.setText("Simpan");
            enableEditText(true);
            button2.setEnabled(true);
            button2.setVisibility(View.VISIBLE);
            return;
        }

        isEdit = false;
        textView1.setText("Detail Barang");
        button1.setText("Edit Data");
        enableEditText(false);

        String id = editText1.getText().toString();
        String nama = editText2.getText().toString();
        String jumlah = editText3.getText().toString();


    }

    protected void enableEditText(Boolean s) {
        editText2.setEnabled(s);
        editText3.setEnabled(s);
    }
}
