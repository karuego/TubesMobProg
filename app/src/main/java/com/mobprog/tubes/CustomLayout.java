package com.mobprog.tubes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomLayout extends LinearLayout {

    private TextView textNama;
    private TextView textJumlah;
    private Button btnLihat;
    private Button btnHapus;

    public CustomLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context ctx) {
        LayoutInflater.from(ctx).inflate(R.layout.custom_layout_table_row, this, true);

        textNama = findViewById(R.id.nama);
        textJumlah = findViewById(R.id.jumlah);
        btnLihat = findViewById(R.id.lihat);
        btnHapus = findViewById(R.id.hapus);
    }

    public void setData(String nama, String jumlah) {
        textNama.setText(nama);
        textJumlah.setText(jumlah);
    }

    public void setFuncLihat(View.OnClickListener fn) {
        btnLihat.setOnClickListener(fn);
    }

    public void setFuncHapus(View.OnClickListener fn) {
        btnHapus.setOnClickListener(fn);
    }
}
