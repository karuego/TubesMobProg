package com.mobprog.tubes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

class ApiMhs {

    static String apiUrl = "https://65af1dcf2f26c3f2139a2a01.mockapi.io/mhs";
    static MainActivity ctx;

    static final String errorData = "[{'nama': '-', 'nim': '-', 'kelas': '-'}]";

    static String getData(String apiUrl) {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("GET");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoOutput(true);

            int resCode = urlConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }

                return response.toString();
            } else {
                return errorData;
            }
        } catch (IOException e) {
            Util.catchErrorPos(e, "ApiMhs.getData, 3");
            return errorData;
        } catch (Exception e) {
            Util.catchErrorPos(e, "ApiMhs.getData");
            return errorData;
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Util.catchErrorPos(e, "ApiMhs.getData, 2");
                }
            }
        }

        /*URL url = null;
        HttpURLConnection urlConn = null;

        try {
            //URL url = new URL(apiUrl);
            //HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            url = new URL(apiUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            //urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoOutput(true);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            Util.catchErrorPos(e, "ApiMhs.getData, 2");
            return null;
        } catch (Exception e) {
            Util.catchErrorPos(e, "ApiMhs.getData");
            return "[{'nama': 'error', 'nim': 'error', 'kelas': 'error'}]";
            //return null;
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }*/
    }

    static String get() {
        return getData(apiUrl);
    }

    static String get(int id) {
        String apiUrl_ = apiUrl + "/" + id;

        return getData(apiUrl_);
    }

    static String post(String jsonData) {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("POST");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
            wr.writeBytes(jsonData);
            wr.flush();
            wr.close();

            int resCode = urlConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }

                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            Util.catchErrorPos(e, "ApiMhs.post");
            //return null;
            return "[{'nama': 'error', 'nim': 'error', 'kelas': 'error'}]";
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Util.catchErrorPos(e, "ApiMhs.post, 2");
                }
            }
        }
    }

    static String put(String id, String jsonData) {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl.concat("/").concat(id));
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("PUT");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
            wr.writeBytes(jsonData);
            wr.flush();
            wr.close();

            int resCode = urlConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }

                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            Util.catchErrorPos(e, "ApiMhs.put");
            //return null;
            return "[{'nama': 'error', 'nim': 'error', 'kelas': 'error'}]";
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Util.catchErrorPos(e, "ApiMhs.put, 2");
                }
            }
        }
    }

    static String delete(String id) {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl.concat("/").concat(id));
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("DELETE");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoOutput(true);

            int resCode = urlConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }

                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            Util.catchErrorPos(e, "ApiMhs.delete");
            return "[{'nama': 'error', 'nim': 'error', 'kelas': 'error'}]";
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Util.catchErrorPos(e, "ApiMhs.delete, 2");
                }
            }
        }
    }
}
