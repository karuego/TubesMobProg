package com.mobprog.tubes;

import android.content.Context;
import android.content.SharedPreferences;

public class Pengaturan {
    private static final String NAMA_FILE = "PengaturanAplikasi";

    protected static final String defApiUrl = "https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs";

    private static String keyApiUrl = "app.url.api";
    private static String keyUserName = "app.user.name";
    private static String keyUserPass = "app.user.pass";

    public static void simpan(Context ctx, String kunci, String nilai) {
        //Util.Setting.set(ctx, kunci, nilai);
        SharedPreferences pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(kunci, nilai);
        editor.apply();
    }

    public static String ambil(Context ctx, String kunci, String nilaiDefault) {
        //Util.Setting.get(ctx, kunci, nilaiDefault);
        SharedPreferences pref = ctx.getSharedPreferences(NAMA_FILE, Context.MODE_PRIVATE);
        return pref.getString(kunci, nilaiDefault);
    }

    public static void simpanApiUrl(Context ctx, String url) {
        //Util.Setting.set(ctx, keyApiUrl, url);
        simpan(ctx, keyApiUrl, url);
    }

    public static void simpanUserName(Context ctx, String name) {
        //Util.Setting.set(ctx, keyUserName, name);
        simpan(ctx, keyUserName, name);
    }

    public static void simpanUserPass(Context ctx, String pass) {
        //Util.Setting.set(ctx, keyUserPass, pass);
        simpan(ctx, keyUserPass, pass);
    }

    protected static String ambilApiUrl(Context ctx) {
        //Util.Setting.get(ctx, keyApiUrl, defApiUrl);
         return ambil(ctx, keyApiUrl, defApiUrl);
    }

    protected static String ambilUserName(Context ctx) {
        //Util.Setting.set(ctx, keyUserName, "");
         return ambil(ctx, keyUserName, "");
    }

    protected static String ambilUserPass(Context ctx) {
        //Util.Setting.set(ctx, keyUserPass, "");
         return ambil(ctx, keyUserPass, "");
    }
}
