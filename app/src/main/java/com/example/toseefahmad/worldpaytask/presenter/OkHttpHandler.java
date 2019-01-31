package com.example.toseefahmad.worldpaytask.presenter;

import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask<String, Void, String> {

    OnTaskCompleted listener;
    OkHttpClient client = new OkHttpClient();

    public OkHttpHandler()
    {
    }

    public void setListener(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String...params) {
        RequestBody body;
        if (params.length == 1)
            body = RequestBody.create(null, new byte[0]);
        else
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params[1]);

        Request request = new Request.Builder()
                .addHeader("Authorization", "replace this text with your token")
                .addHeader("Content-Type", "application/vnd.worldpay.payments-v0.4+json")
                .addHeader("Accept", "application/vnd.worldpay.payments-v0.4+json")
                .url(params[0])
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onTaskCompleted(s);
    }

}
