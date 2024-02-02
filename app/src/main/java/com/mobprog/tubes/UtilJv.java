package com.mobprog.tubes;

import android.app.Activity;
//import android.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import java.util.Objects;

class UtilJv {
    void showMessage(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    void showMessage(Context ctx, String s, int length) {
        Toast.makeText(ctx, s, length).show();
    }

    AlertDialog showDialog(
        Context ctx, String msg, String title_,
        String pBtn_, DialogInterface.OnClickListener pFunc,
        String nBtn, DialogInterface.OnClickListener nFunc
    ) {
        String title = title_;
        String pBtn = pBtn_;

        if (title_ == null) title = "Info";
        if (pBtn_ == null) title = "Ok";

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton(pBtn, (DialogInterface dialog, int which) -> {
            if (pFunc != null) {
                pFunc.onClick(dialog, which);
            } else {
                dialog.dismiss();
            }
        });

        if (nBtn != null) {
            builder.setNegativeButton(nBtn, nFunc);
        }

        AlertDialog alertDialog = builder.create();

        ((Activity) ctx).runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   alertDialog.show();
               }
        });

        return alertDialog;
    }

    AlertDialog showDialog(Context ctx, String msg) {
        return showDialog(ctx, msg, "Info", "Ok", null, null, null);
    }

    AlertDialog showDialog(Context ctx, String msg, String title) {
        return showDialog(ctx, msg, title);
    }

    AlertDialog showDialogInfo(Context ctx, String msg) {
        return showDialog(ctx, msg);
    }

    AlertDialog showDialogError(Context ctx, String msg) {
        return showDialog(ctx, msg, "Error");
    }

    AlertDialog showDialogError(Context ctx, String msg, String title) {
        return showDialog(ctx, msg, title);
    }

    void hideDialog(Context ctx, AlertDialog dialog) {
        ((Activity) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    void logError(Exception e) {
        e.printStackTrace();
        Log.e("MobProgErr", "ada error \n########\n ", e);
    }

    void logErrorMsg(Exception e, String msg) {
        e.printStackTrace();
        Log.e("MobProgErr", "ada error \n########\n $msg", e);
    }

    void logErrorPos(Exception e, String pos) {
        String msg = "> on $pos \n--------\n";

        logErrorMsg(e, msg);
    }

    void catchError(Context ctx, Exception e) {
        logError(e);
        showDialog(ctx, e.toString());
    }

    void catchError(Context ctx, Exception e, String title) {
        logError(e);
        showDialog(ctx, e.toString(), title);
    }

    void catchError(Context ctx, Exception e, String title, String msg) {
        logError(e);
        showDialog(ctx, "$msg \n--------\n $e", title);
    }

    void catchErrorPos(Context ctx, Exception e, String pos) {
        String msg = "> on $pos \n--------\n";

        logErrorMsg(e, msg);
        showDialog(ctx, msg + e.toString());
    }

    void catchErrorPos(Context ctx, Exception e, String title, String pos) {
        String msg = "> on $pos \n--------\n";

        logErrorMsg(e, msg);
        showDialog(ctx, msg + e.toString(), title);
    }

    void simulateBlocking(Long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            logError(e);
        }
    }

    void simulateBlocking() {
        simulateBlocking(2000L);
    }

    static class Setting {
        private static String NAMA_FILE = "PengaturanAplikasi";

        public static void set(Context ctx, String key, String value) {
            SharedPreferences pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, value);
            editor.apply();
        }

        String get(Context ctx, String kunci, String defVal) {
            SharedPreferences pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE);
            return pref.getString(kunci, defVal);
        }
    }

    interface Callback {
        void onResult(String result);
        void onError(String errorMessage);
        default void onDone() {
            // Implementasi default (kosong)
        }
    }
}
