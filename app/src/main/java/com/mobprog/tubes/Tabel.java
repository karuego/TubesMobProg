package com.mobprog.tubes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Tabel {

    //static MainActivity ctx;
    static Context ctx;
    static TableLayout tbl;

    static void muat(String result) {
        hapus();
        tambah(result);
    }

    static void tambah(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            List<JSONObject> jsonList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++)
                jsonList.add(jsonArray.getJSONObject(i));

            // Collections.shuffle(jsonList);

            for (JSONObject jsonObject : jsonList)
                Tabel.addRow(jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(e, "Error Parsing JSON", "Tabel.tambah");
        } catch (Exception e) {
            Util.catchErrorPos(e, "Tabel.tambah", "Tabel.tambah, 2");
        }
    }

    static void tambahSatu(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Tabel.addRow(jsonObject);
        } catch (JSONException e) {
            Util.catchErrorPos(e, "JSON Parsing Error", "Tabel.tambahSatu");
        } catch (Exception e) {
            Util.catchErrorPos(e, "Tabel.tambahSatu");
        }
    }

    static void hapus() {
        tbl.removeViews(1, tbl.getChildCount() - 1);
    }

    static void hapus(int id) {
        tbl.removeViews(1, id + 1);
    }

    static void addRow(JSONObject jsonObj) {
        try {
            String nama = jsonObj.getString("nama");
            String nim = jsonObj.getString("nim");
            String kelas = jsonObj.getString("kelas");

            TableRow tableRow = new TableRow(ctx);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            tableRowParams.setMargins(0, 0, 0, 0);
            tableRow.setLayoutParams(tableRowParams);

            TextView tvNama = new TextView(ctx);
            TextView tvNim = new TextView(ctx);
            TextView tvKelas = new TextView(ctx);

            tvNama.setText(nama);
            tvNim.setText(nim);
            tvKelas.setText(kelas);

            tvNim.setGravity(Gravity.CENTER);
            tvKelas.setGravity(Gravity.CENTER);

            tableRow.addView(tvNama);
            tableRow.addView(tvNim);
            tableRow.addView(tvKelas);

            tbl.setOrientation(TableLayout.VERTICAL);
            tbl.addView(tableRow);
        } catch(JSONException e) {
            Util.catchErrorPos(e, "JSON Parsing Error", "Tabel.addRow");
        } catch (Exception e) {
            Util.catchErrorPos(e, "Tabel.addRow");
        }
    }
}
