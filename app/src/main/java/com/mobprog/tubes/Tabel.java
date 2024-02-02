package com.mobprog.tubes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    static void hapus() {
        tbl.removeViews(1, tbl.getChildCount() - 1);
    }

    static void hapus(int id) {
        tbl.removeViews(1, id + 1);
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

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            linearLayoutParams.weight = 1.0f;

            LinearLayout kontainer = new LinearLayout(ctx);
            Button btnLihat = new Button(ctx);
            Button btnHapus = new Button(ctx);
            TextView tvId = new TextView(ctx);
            TextView tvNama = new TextView(ctx);
            TextView tvJumlah = new TextView(ctx);

            tvId.setLayoutParams(linearLayoutParams);
            tvNama.setLayoutParams(linearLayoutParams);
            tvJumlah.setLayoutParams(linearLayoutParams);
            btnLihat.setLayoutParams(linearLayoutParams);
            btnHapus.setLayoutParams(linearLayoutParams);

            tvId.setText(nama);
            tvNama.setText(nim);
            tvJumlah.setText(kelas);

            tvId.setGravity(Gravity.CENTER);
            tvNama.setGravity(Gravity.CENTER);
            tvJumlah.setGravity(Gravity.CENTER);

            tvId.setWidth(0);
            tvNama.setWidth(0);
            tvJumlah.setWidth(0);

            btnLihat.setWidth(2);
            btnLihat.setHeight(2);

            btnHapus.setWidth(2);
            btnHapus.setHeight(2);

            kontainer.setOrientation(LinearLayout.HORIZONTAL);
            kontainer.setWeightSum(3);
            kontainer.setClickable(true);

            View.OnClickListener fnOnClick = v -> {
                Util.showMessage(ctx, "data " + tvId.getText().toString());
            };

            View.OnLongClickListener fnOnLongClick = v -> {
                Util.showMessage(ctx, "long click; data " + tvId.getText().toString());
                return true;
            };

            /*tvId.setOnClickListener(fnOnClick);
            tvNama.setOnClickListener(fnOnClick);
            tvJumlah.setOnClickListener(fnOnClick);
            kontainer.setOnClickListener(fnOnClick);*/
            btnLihat.setOnClickListener(fnOnClick);

            tvId.setOnLongClickListener(fnOnLongClick);
            tvNama.setOnLongClickListener(fnOnLongClick);
            tvJumlah.setOnLongClickListener(fnOnLongClick);
            kontainer.setOnLongClickListener(fnOnLongClick);

            kontainer.addView(tvId);
            kontainer.addView(tvNama);
            kontainer.addView(tvJumlah);
            kontainer.addView(btnLihat);

            //tableRow.addView(tvId);
            //tableRow.addView(tvNama);
            //tableRow.addView(tvJumlah);
            tableRow.addView(kontainer);

            tbl.setOrientation(TableLayout.VERTICAL);
            tbl.addView(tableRow);
        } catch(JSONException e) {
            Util.catchErrorPos(ctx, e, "JSON Parsing Error", "Tabel.addRow");
        } catch (Exception e) {
            Util.catchErrorPos(ctx, e, "Tabel.addRow");
        }
    }
}
