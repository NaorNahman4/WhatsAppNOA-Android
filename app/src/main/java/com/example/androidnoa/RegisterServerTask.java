package com.example.androidnoa;

import android.os.AsyncTask;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterServerTask extends AsyncTask<String, Void, Integer> {
    private RegisterCallback callback;

    public RegisterServerTask(RegisterCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String data = params[0];

        try {
            URL url = new URL("http://localhost:8080/api/Users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        if (callback != null) {
            callback.onRegistrationComplete(statusCode);
        }
    }

    public interface RegisterCallback {
        void onRegistrationComplete(int statusCode);
    }
}
