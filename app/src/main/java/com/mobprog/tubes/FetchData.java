package com.mobprog.tubes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FetchData extends AsyncTask<Void, Void, String> {

    private TableLayout tableLayout;
    private MainActivity ctx;

    public FetchData(TableLayout t, MainActivity ctx_) {
        this.tableLayout = t;
        this.ctx = ctx_;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            //URL url = new URL("http://lab.halohrev.com/api/siswa");
            //URL url = new URL("http://localhost:8080/response.json");
            //URL url = new URL("https://github.com/karuego/test_api/raw/main/priv/mhs/mobprog_tubes.json");
            URL url = new URL("https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setRequestMethod("GET");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                return stringBuilder.toString();
            } catch (IOException e) {
                Util.catchErrorPos(e, "FetchData.doInBackground");
                return null;
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Util.catchErrorPos(e, "FetchData.doInBackground, 2");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.e("DataRetrievalError", "Failed to retrieve data from the server.");
            return;
        }

        try {
            tambahKeTabel(result);
        } catch (JSONException e) {
            Util.catchErrorPos(e, "FetchData.onPostExecute");
            Log.e("JSONParsingError", "Error parsing JSON: " + e.getMessage());
        }
    }

    private void tambahKeTabel(String result) throws JSONException {
        JSONArray jsonArray = new JSONArray(result);
        List<JSONObject> jsonList = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArray.length(); i++)
            jsonList.add(jsonArray.getJSONObject(i));

        // Collections.shuffle(jsonList);
        tableLayout.setOrientation(TableLayout.VERTICAL);

        for (JSONObject jsonObject : jsonList) {
            String nama = jsonObject.getString("nama");
            String nim = jsonObject.getString("nim");
            String kelas = jsonObject.getString("kelas");
            TableRow tableRow = new TableRow(ctx);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            tableRowParams.setMargins(0, 0, 0, 0);
            tableRow.setLayoutParams(tableRowParams);

            TextView textViewNama = new TextView(ctx);
            TextView textViewNIM = new TextView(ctx);
            TextView textViewKelas = new TextView(ctx);

            textViewNama.setText(nama);
            textViewNIM.setText(nim);
            textViewKelas.setText(kelas);

            textViewNIM.setGravity(Gravity.CENTER);
            textViewKelas.setGravity(Gravity.CENTER);

            tableRow.addView(textViewNama);
            tableRow.addView(textViewNIM);
            tableRow.addView(textViewKelas);

            tableLayout.addView(tableRow);
        }
    }
}
