package com.mobprog.tubes

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import java.util.Objects

internal interface FuncDialog {
    fun onClick(dialog: DialogInterface?, which: Int)
}

interface Callback {
    fun onResult(result: String)
    fun onError(errorMessage: String)
    fun onDone() {
        // Implementasi default (kosong)
    }
}

internal object Util {
    //@JvmStatic
    //lateinit var appCtx: Context

    @JvmStatic
    fun showMessage(ctx: Context?, s: String?) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showMessage(ctx: Context?, s: String?, length: Int) {
        Toast.makeText(ctx, s, length).show()
    }

    @JvmStatic
    fun showDialog(
        ctx: Context, msg: String, title: String = "Info",
        pBtn: String = "Ok", pFunc: FuncDialog? = null,
        nBtn: String? = null, nFunc: FuncDialog? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(pBtn) { dialog: DialogInterface, which: Int ->
            if (pFunc != null) {
                pFunc.onClick(dialog, which)
            } else {
                dialog.dismiss()
            }
        }
        if (nBtn != null) {
            builder.setNegativeButton(nBtn) { dialog: DialogInterface?, which: Int ->
                nFunc?.onClick(dialog!!, which)
            }
        }
        val alertDialog = builder.create()

        (ctx as Activity).runOnUiThread(Runnable {
            alertDialog.show()
        })

        return alertDialog
    }

    @JvmStatic
    fun showDialog2(
        ctx: Context, msg: String, title: String = "Info",
        pBtn: String? = "Ok", pFunc: FuncDialog? = null,
        nBtn: String? = null, nFunc: FuncDialog? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(title)
        builder.setMessage(msg)

        if (pBtn != null) {
            builder.setPositiveButton(pBtn) { dialog: DialogInterface, which: Int ->
                if (pFunc != null) {
                    pFunc.onClick(dialog, which)
                } else {
                    dialog.dismiss()
                }
            }
        }

        if (nBtn != null) {
            builder.setNegativeButton(nBtn) { dialog: DialogInterface?, which: Int ->
                nFunc?.onClick(dialog!!, which)
            }
        }

        val alertDialog = builder.create()

        (ctx as Activity).runOnUiThread(Runnable {
            alertDialog.show()
        })

        return alertDialog
    }

    @JvmStatic
    fun showDialog(ctx: Context, msg: String): AlertDialog {
        return showDialog(ctx, msg)
    }

    @JvmStatic
    fun showDialog(ctx: Context, msg: String, title: String): AlertDialog {
        return showDialog(ctx, msg, title)
    }

    @JvmStatic
    fun showDialogInfo(ctx: Context, msg: String): AlertDialog {
        return showDialog(ctx, msg)
    }

    @JvmStatic
    fun showDialogError(ctx: Context, msg: String): AlertDialog {
        return showDialog(ctx, msg, "Error")
    }

    @JvmStatic
    fun showDialogError(ctx: Context, msg: String, title: String): AlertDialog {
        return showDialog(ctx, msg, title)
    }

    @JvmStatic
    fun hideDialog(ctx: Context, dialog: AlertDialog) {
        (ctx as Activity).runOnUiThread {
            dialog.dismiss()
        }
    }

    @JvmStatic
    fun logError(e: Exception) {
        e.printStackTrace()
        Log.e("MobProgErr", "ada error \n########\n ", e)
    }

    @JvmStatic
    fun logErrorMsg(e: Exception, msg: String) {
        e.printStackTrace()
        Log.e("MobProgErr", "ada error \n########\n $msg", e)
    }

    @JvmStatic
    fun logErrorPos(e: Exception, pos: String) {
        val msg = "> on $pos \n--------\n"

        logErrorMsg(e, msg)
    }

    @JvmStatic
    fun catchError(e: Exception) {
        logError(e)
        showDialog(Tabel.ctx, e.toString())
    }

    @JvmStatic
    fun catchError(e: Exception, title: String) {
        logError(e)
        showDialog(Tabel.ctx, e.toString(), title)
    }

    @JvmStatic
    fun catchError(e: Exception, title: String, msg: String) {
        logError(e)
        showDialog(Tabel.ctx, "$msg \n--------\n $e", title)
    }

    @JvmStatic
    fun catchErrorPos(e: Exception, pos: String) {
        val msg = "> on $pos \n--------\n"

        logErrorMsg(e, msg)
        showDialog(Tabel.ctx, msg + e.toString())
    }

    @JvmStatic
    fun catchErrorPos(e: Exception, title: String, pos: String) {
        val msg = "> on $pos \n--------\n"

        logErrorMsg(e, msg)
        showDialog(Tabel.ctx, msg + e.toString(), title)
    }

    @JvmStatic
    fun simulateBlocking(delay: Long) {
        try {
            Thread.sleep(delay);
        } catch (e: InterruptedException) {
            logError(e)
        }
    }

    @JvmStatic
    fun simulateBlocking() {
        simulateBlocking(2000)
    }

    object Setting {
        @JvmStatic
        private var NAMA_FILE = "PengaturanAplikasi"

        @JvmStatic
        fun set(ctx: Context, key: String, value: String) {
            val pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString(key, value)
            editor.apply()
        }

        @JvmStatic
        fun get(ctx: Context, kunci: String, defVal: String): String {
            val pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE)
            return pref.getString(kunci, defVal)!!
        }
    }
}
