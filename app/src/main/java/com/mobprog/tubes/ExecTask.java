package com.mobprog.tubes;

import android.os.AsyncTask;

public class ExecTask extends AsyncTask<Void, Void, Void> {
    Func func;

    public ExecTask(Func f) {
        func = f;
    }

    @Override
    protected void onPreExecute() {
        func.pre();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        func.on();
        return null;
    }

    @Override
    protected void onPostExecute(Void a_) {
        func.post();
    }

    interface Func {
        default void on() {}
        default void pre() {}
        default void post() {}
    }
}
