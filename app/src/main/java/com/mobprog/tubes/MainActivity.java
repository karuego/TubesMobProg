package com.mobprog.tubes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {
    private Context ctx;
    protected TableLayout tbl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        final LinearLayout linearLayoutX = findViewById(R.id.linearLayoutX);

        final Button btn1 = findViewById(R.id.button1);
        final Button btn2 = findViewById(R.id.button2);
        tbl1 = findViewById(R.id.tableLayout1);

        //appCtx = getApplicationContext();
        Tabel.tbl = tbl1;

        //muatTabel();

        btn1.setOnClickListener(this::btn1_OnClick);
        btn2.setOnClickListener(v -> muatTabel());

        linearLayoutX.setOnClickListener(this::showDialogLogout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            linearLayoutX.setTooltipText("Tekan untuk Logout");
        }
    }

    protected void btn1_OnClick(View v) {
        Intent pindah;

        if (Pengaturan.ambilUserName(getApplicationContext()).isBlank()) {
            pindah = new Intent(this, LoginActivity.class);
        } else {
            pindah = new Intent(this, DataTambahActivity.class);

        }

        startActivity(pindah);
    }

    protected void muatTabel() {
        var dialog = Util.showDialog2(this, "Memuat...", "Info", null, null, "Batal", (dialog_, which) -> {
            showMessage("Batal");
        });

        ExecutorService exe = Executors.newSingleThreadExecutor();
        Callable<String> myRun = () -> {
            try {
                URL url = new URL("https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    final StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (final IOException e) {
                e.printStackTrace();
                showDialogError("Error ::: \n\n" + e.getMessage());
                return "";
            }
        };

        //Future<?> future = exe.submit(myRun);
        Future<String> future = exe.submit(myRun);
        exe.submit(() -> {
            try {
                String res = future.get();
                String result;

                if (res.isBlank()) {
                    result = "[{'nama': '-', 'jumlah': '-'}]";
                    runOnUiThread(() -> {
                        Tabel.muat2(this, result);
                        dialog.dismiss();
                        Util.showDialog2(this, "Gagal memuat data", "Error", "Ok", null, null, null);
                    });
                } else {
                    result = res;
                    runOnUiThread(() -> {
                        Tabel.muat2(this, result);
                        dialog.dismiss();
                    });
                }
            } catch (ExecutionException | InterruptedException e) {
                Util.logError(e);
                throw new RuntimeException(e);
            } catch (Exception e) {
                Util.logError(e);
            }
        });

        exe.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings ditekan", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void showDialogLogout(View v) {
        if (Pengaturan.ambilUserName(getApplicationContext()).isBlank()) {
            return;
        }

        var builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi logout");
        builder.setMessage("Anda ingin logout??");

        builder.setPositiveButton("Ya", (var dialog, var which) -> {
            Pengaturan.simpanUserName(this, "");
            Pengaturan.simpanUserPass(this, "");
            showMessage("Anda telah logout !");
            dialog.dismiss();
        });

        builder.setNegativeButton("Tidak", (var dialog, var which) -> {
            dialog.dismiss();
        });

        var alertDialog = builder.create();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }
}
