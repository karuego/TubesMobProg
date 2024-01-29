package com.mobprog.tubes;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

interface FuncPreExec {
    void fn();
}

interface FuncProcess {
    String fn();
}

interface FuncSuccess {
    void fn(String res);
}

public class ApiTask extends AsyncTask<Void, Void, String> {

    private String result;
    private FuncPreExec fnPreExec;
    private FuncProcess fnProcess;
    private FuncSuccess fnSuccess;

    static protected AlertDialog dialog;

    protected void setFnPreExec(FuncPreExec _fn) {
        fnPreExec = _fn;
    }

    protected void setFnProcess(FuncProcess _fn) {
        fnProcess = _fn;
    }

    protected void setFnSuccess(FuncSuccess _fn) {
        fnSuccess = _fn;
    }

    protected String getResult() {
        return this.result;
    }

    @Override
    protected void onPreExecute() {
        fnPreExec.fn();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return fnProcess.fn();
    }

    @Override
    protected void onPostExecute(String result) {
        /*if (result != null) {
            //Log.e("DataRetrievalError", "Failed to retrieve data from the server.");
            Log.e("MobProgErr", "Failed to retrieve data from the server.");
            return;
        }*/

        //Tabel.muat(result);
        this.result = result;
        fnSuccess.fn(result);
    }
}
