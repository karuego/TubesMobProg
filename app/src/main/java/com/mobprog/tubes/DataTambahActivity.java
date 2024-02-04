package com.mobprog.tubes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataTambahActivity extends AppCompatActivity {

    protected Button button1;
    protected EditText editText1;
    protected EditText editText2;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tambah);
        ctx = this;

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(this::button1_OnClick);
    }

    protected void button1_OnClick(View v) {
        final String nama = editText1.getText().toString();
        final String jumlah = editText2.getText().toString();
        final String jsonData = "{\"nama\":\"" + nama + "\",\"jumlah\":\"" + jumlah + "\"}";

        ExecutorService exe = Executors.newSingleThreadExecutor();
        try {
            Callable<String> myRun = () -> {
                try {
                    URL url = new URL("https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs");
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

                    urlConn.setRequestMethod("POST");
                    urlConn.setInstanceFollowRedirects(true);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setDoOutput(true);

                    try (DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream())) {
                        wr.writeBytes(jsonData);
                        wr.flush();
                    }

                    //int resCode = urlConn.getResponseCode();
                    //if (resCode == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
                            StringBuilder response = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null) {
                                response.append(line).append('\n');
                            }

                            return response.toString();
                        }
                    //} else {
                    //    return null;
                    //}
                } catch (final IOException e) {
                    e.printStackTrace();
                    showDialogError("Error ::: \n\n" + e.getMessage());
                    return "";
                }
            };

            Future<String> future = exe.submit(myRun);
            exe.submit(() -> {
                try {
                    String res = future.get();

                    if (!res.startsWith("{")) {
                        ((Activity) ctx).runOnUiThread(() -> showDialogError("Gagal menambahkan data"));
                    } else {
                        JSONObject jObj = new JSONObject(res);
                        String id_ = jObj.getString("id");
                        String nama_ = jObj.getString("nama");
                        String jumlah_ = jObj.getString("jumlah");

                        ((Activity) ctx).runOnUiThread(() -> {
                            showDialogInfo("Data berhasil ditambahkan.\nId : "+id_+"\nBarang : "+nama_+"\nJumlah :" + jumlah_);
                        });
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } finally {
            exe.shutdown();
        }
    }

    public void showMessage(String s_) {
        Toast.makeText(this, s_, Toast.LENGTH_SHORT).show();
    }

    public AlertDialog showDialogInfo(String msg) {
        var builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setMessage(msg);

        builder.setPositiveButton("Ok", (var dialog, var which) -> {
            dialog.dismiss();
        });

        var alertDialog = builder.create();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return alertDialog;
    }

    public AlertDialog showDialogInfo2(Context ctx, String msg) {
        var builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Info");
        builder.setMessage(msg);

        var alertDialog = builder.create();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
        return alertDialog;
    }

    public AlertDialog showDialogError(String msg) {
        var builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(msg);

        builder.setPositiveButton("Ok", (var dialog, var which) -> {
            dialog.dismiss();
        });

        var alertDialog = builder.create();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });

        return alertDialog;
    }
}
