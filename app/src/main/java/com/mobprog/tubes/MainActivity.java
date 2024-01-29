package com.mobprog.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;
import android.app.AlertDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {
    private ApiTask data;
    //private FetchData data;
    private ApiMhs mhs;
    protected TableLayout tbl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        tbl1 = findViewById(R.id.tableLayout1);

        //appCtx = getApplicationContext();
        Tabel.ctx = /*baseCtx =*/ getBaseContext();
        //Tabel.ctx = this;
        Tabel.tbl = tbl1;

        //muatTabel();

        btn1.setOnClickListener(v -> {
            showMessage("Data belum bisa ditambah 😢");
        });

        btn2.setOnClickListener(v -> muatTabel());
    }

    protected void muatTabel() {
        //AlertDialog dialog = showDialogInfo2("Memuat...");
        var dialog = Util.showDialog2(this, "Memuat...", "Info", null, null, "Batal", (dialog_, which) -> {
            showMessage("Batal");
        });

        class Response {
            String res;
            int code;
            Response(String r, int c) {
                res = r; code = c;
            }
        }

        ExecutorService exe = Executors.newSingleThreadExecutor();
        Callable<Response> myRun = () -> {
            Util.simulateBlocking(3000);
            return new Response(
                "[{'nama': 'iksan', 'nim': '098', 'kelas': 'C'}]",
                404
            );
        };
//        Future<?> future = exe.submit(myRun);
        Future<Response> future = exe.submit(myRun);
        exe.submit(() -> {

            try {
                Response res = future.get();
                String result;

                if (res.code != 200) {
                    result = "[{'nama': '-', 'nim': '-', 'kelas': '-'}]";
                    runOnUiThread(() -> {
                        //Tabel.muat("[{'nama': 'iksan', 'nim': '098', 'kelas': 'C'}]");
                        Tabel.muat(result);
                        dialog.dismiss();
                        Util.showDialog2(this, "Gagal memuat data", "Error", "Ok", null, null, null);
                    });
                } else {
                    result = res.res;
                    runOnUiThread(() -> {
                        //Tabel.muat("[{'nama': 'iksan', 'nim': '098', 'kelas': 'C'}]");
                        Tabel.muat(result);
                        dialog.dismiss();
                    });
                }
            } catch (ExecutionException e) {
                Util.logError(e);
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Util.logError(e);
                throw new RuntimeException(e);
            } catch (Exception e) {
                Util.logError(e);
            }
        });
        exe.shutdown();


        /*NetTask.performNetworkRequest(new NetTask.Callback() {
            @Override
            public void onResult(String result) {
                // Menggunakan hasil operasi jaringan di sini
                // Misalnya, Anda dapat memperbarui UI dengan hasilnya
                showDialogInfo("Result: " + result);
                Tabel.muat(result);
            }

            @Override
            public void onError(String errorMessage) {
                // Menangani kesalahan yang terjadi
                showDialogError("Terjadi kesalahan: " + errorMessage);
            }

            @Override
            public void onDone() {
                dialog.dismiss();
            }
        });*/


        if (data == null || data.getStatus() == AsyncTask.Status.FINISHED) {
            showMessage("Loading data...");
            /*tbl1.removeViews(1, tbl1.getChildCount() - 1);
            data = new FetchData(tbl1, MainActivity.this);
            data.execute();*/

            /*ExecApi exApi = new ExecApi();
            exApi.func = ApiMhs::get;
            exApi.execute();*/

            /*ApiMhs.ctx = MainActivity.this;

            data = new ExecApi();
            data.setFunc(() -> {
                return ApiMhs.get();
            });
            //data.func = ApiMhs::get;
            data.execute();*/

            /*final AlertDialog[] dialog = new AlertDialog[1];
            data = new ApiTask();
            data.setFnPreExec(() -> {
                Tabel.ctx.runOnUiThread(() -> {
                    dialog[0] = showDialogInfo("Memuat....");
                });
            });
            data.setFnProcess(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ApiMhs.get(1);
                //return "[{'nama': 'error', 'nim': 'error', 'kelas': 'error'}]";
            });
            //data.setFnSuccess(result -> Util.showDialogInfo(getApplicationContext(), result));
            data.setFnSuccess(res -> {
                //Tabel.muat(res);
                showDialogInfo(res);
                runOnUiThread(() -> dialog[0].dismiss());
            });
            data.execute();*/
        }
    }

    private void login() {
        //
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
            //return true;
            Toast.makeText(this, "Settings ditekan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_login) {
            login();
        } else if (id == R.id.action_refresh) {
            muatTabel();
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
        alertDialog.show();
        return alertDialog;
    }

    public AlertDialog showDialogInfo2(Context ctx, String msg) {
        var builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Info");
        builder.setMessage(msg);

        var alertDialog = builder.create();
        alertDialog.show();
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
        alertDialog.show();
        return alertDialog;
    }
}
