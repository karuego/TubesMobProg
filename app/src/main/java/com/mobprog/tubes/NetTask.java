package com.mobprog.tubes;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class NetTask {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Executor executor = Executors.newSingleThreadExecutor();


    public static void performNetworkRequest(final Callback callback) {
        /*Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    // Network operation (e.g., connecting to a server) is performed here
                    URL url = new URL(ApiMhs.apiUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // ... (set properties, read response, etc.)

                    // Get the response
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    return stringBuilder.toString();
                } catch (MalformedURLException e) {
                    return "URL tidak valid: " + e.toString();
                } catch (IOException e) {
                    //Util.catchErrorPos(e, "ApiMhs.getData, 2");
                    return "Terjadi kesalahan IO" + e.toString();
                } catch (Exception e) {
                    //Util.catchErrorPos(e, "ApiMhs.getData");
                    return "Terjadi kesalahan : " + e.toString();
                }
            }
        });*/

        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    // Operasi jaringan (contoh: koneksi ke server) dilakukan di sini
                    URL url = new URL(ApiMhs.apiUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // ... (atur properti, baca respons, dll.)

                    // Mendapatkan respons
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    return stringBuilder.toString();
                } catch (IOException e) {
                    // Tangani pengecualian IO
                    return "Error: " + e.getMessage();
                } catch (Exception e) {
                    // Tangani pengecualian lainnya
                    return "Error: " + e.getMessage();
                }
            }
        }, executor).thenAccept(result -> {
            callback.onDone();

            if (result.startsWith("Error:")) {
                // Terjadi kesalahan, memberi tahu callback
                callback.onError(result.substring("Error: ".length()));
            } else {
                // Tidak ada kesalahan, memberi tahu callback dengan hasilnya
                callback.onResult(result);
            }
        });

        // Perform something after the network operation is complete
        /*try {
            String result = future.get(); // Get the result of the network operation
            if (result.startsWith("Error:")) {
                // Error occurred, notify the callback
                callback.onError(result.substring("Error: ".length()));
            } else {
                // No error, notify the callback with the result
                callback.onResult(result);
            }
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            callback.onError("Unexpected error occurred");
        }*/
    }

    public interface Callback {
        void onResult(String result);
        void onError(String errorMessage);
        default void onDone() {
            // Implementasi default (kosong)
        }
    }
}
