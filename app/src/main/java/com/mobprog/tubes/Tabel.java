package com.mobprog.tubes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Tabel {

    static TableLayout tbl;

    static void muat(Context ctx, String result) {
        hapus();
        tambah(ctx, result);
    }

    static void muat2(Context ctx, String result) {
        reset();
        tambah2(ctx, result);
    }

    static void tambah(Context ctx, String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            List<JSONObject> jsonList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++)
                jsonList.add(jsonArray.getJSONObject(i));

            // Collections.shuffle(jsonList);

            for (JSONObject jsonObject : jsonList)
                Tabel.addRow(ctx, jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(ctx, e, "Error Parsing JSON", "Tabel.tambah");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.tambah", "Tabel.tambah, 2");
        }
    }

    static void tambah2(Context ctx, String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            List<JSONObject> jsonList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++)
                jsonList.add(jsonArray.getJSONObject(i));

            // Collections.shuffle(jsonList);

            for (JSONObject jsonObject : jsonList)
                Tabel.addRow2(ctx, jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(ctx, e, "Error Parsing JSON", "Tabel.tambah");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.tambah", "Tabel.tambah, 2");
        }
    }

    static void tambahSatu(Context ctx, String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Tabel.addRow(ctx, jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(ctx, e, "JSON Parsing Error", "Tabel.tambahSatu");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.tambahSatu");
        }
    }

    static void tambahSatu2(Context ctx, String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Tabel.addRow2(ctx, jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(ctx, e, "JSON Parsing Error", "Tabel.tambahSatu");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.tambahSatu");
        }
    }

    static void reset() {
        tbl.removeAllViews();
    }

    static void hapus() {
        tbl.removeViews(1, tbl.getChildCount() - 1);
    }

    static void hapus(int n) {
        tbl.removeViewAt(n);
    }

    static void addRow(Context ctx, JSONObject jsonObj) {
        try {
            String nama = jsonObj.getString("id");
            String nim = jsonObj.getString("nama");
            String kelas = jsonObj.getString("jumlah");

            TableRow tableRow = new TableRow(ctx);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            tableRowParams.setMargins(0, 0, 0, 0);
            tableRow.setLayoutParams(tableRowParams);

            TextView tvId = new TextView(ctx);
            TextView tvNama = new TextView(ctx);
            TextView tvJumlah = new TextView(ctx);

            tvId.setText(nama);
            tvNama.setText(nim);
            tvJumlah.setText(kelas);

            tvNama.setGravity(Gravity.CENTER);
            tvJumlah.setGravity(Gravity.CENTER);

            tableRow.addView(tvId);
            tableRow.addView(tvNama);
            tableRow.addView(tvJumlah);

            tbl.setOrientation(TableLayout.VERTICAL);
            tbl.addView(tableRow);
        } catch(JSONException e) {
            Util.catchErrorPos(ctx, e, "JSON Parsing Error", "Tabel.addRow");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.addRow");
        }
    }

    static void addRow2(Context ctx, JSONObject jsonObj) {
        try {
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            );
            tableRowParams.setMargins(0, 0, 0, 0);

            String id = jsonObj.getString("id");
            String nama = jsonObj.getString("nama");
            String jumlah = jsonObj.getString("jumlah");

            TableRow tableRow = new TableRow(ctx);
            tableRow.setLayoutParams(tableRowParams);

            CustomLayout customLayout = new CustomLayout(ctx);
            customLayout.setData(nama, jumlah);
            customLayout.setFuncLihat(v -> {
                Util.showDialog(ctx, "Id : " + id + "\nBarang : " + nama + "\nJumlah : " + jumlah);
            });
            customLayout.setFuncHapus(v -> {
                tbl.removeView(tableRow);

                ExecutorService exe = Executors.newSingleThreadExecutor();
                Callable<String> myRun = () -> {
                    try {
                        URL url = new URL("https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs/" + id);
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

                        urlConn.setRequestMethod("DELETE");
                        urlConn.setInstanceFollowRedirects(true);
                        urlConn.setRequestProperty("Content-Type", "application/json");
                        urlConn.setDoOutput(true);

                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                            final StringBuilder stringBuilder = new StringBuilder();

                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }

                            return stringBuilder.toString();
                        } finally {
                            urlConn.disconnect();
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                        Util.showDialogError(ctx, "Error ::: \n\n" + e.getMessage());
                        return "";
                    }
                };

                Future<String> future = exe.submit(myRun);
                exe.submit(() -> {
                    try {
                        String res = future.get();
                        String result;

                        if (res.isBlank()) {
                            result = "[{'nama': '-', 'jumlah': '-'}]";
                            ((Activity) ctx).runOnUiThread(() -> {
                                Util.showDialog2(ctx, "Gagal menghapus data", "Error", "Ok", null, null, null);
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
            });

            tableRow.addView(customLayout);

            tbl.setOrientation(TableLayout.VERTICAL);
            tbl.addView(tableRow);
        } catch(JSONException e) {
            Util.catchErrorPos(ctx, e, "JSON Parsing Error", "Tabel.addRow");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.addRow");
        }
    }
}
