package com.example.user.api;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Connection;

public class BaseApi {

    public BaseApi() {
    }

    protected String appKey = "appKey=yx-1PU73oUj6gfk0hNyrNUwhWnmBRld7-SfKAU7Kg6Fpp43anR261KDiQ-MY4P2SRwH_cd4Py1OCY5jpPnY_Viyzja-s18njTLc0E7XcZFwwvi32zX-B91Sdwq1KeZ7m";
    protected String baseUrl = "http://onlinestore.whitetigersoft.ru/api";
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client != null) {
            return client;
        } else {
            client = new OkHttpClient();
            return client;
        }
    }

    protected interface Listener {
        void onSuccess(JSONObject jsonObject);

        void onSuccess(JSONArray jsonArray);

        // void onFailure(int request);
        void onFailure(String request);
    }

    // TODO: 07.11.2018 add sendRequestMethod

    protected void sendRequest(Request request, final AppCompatActivity appCompatActivity, final Listener listener/*requestType, url, liastParams, Listener*/) {
        OkHttpClient client = BaseApi.getClient();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                listener.onFailure(call.request().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            Object object = json.get("data");
                            if (object instanceof JSONObject) {
                                listener.onSuccess(json.getJSONObject("data"));
                            } else if (object instanceof JSONArray) {
                                listener.onSuccess(json.getJSONArray("data"));
                            } else {
                                Toast.makeText(appCompatActivity, "Ошибка формата данных с сервера", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(appCompatActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * Listener
     * #onSuccess(JsonObject)
     * #onSuccess(JsonArray)
     * #onFailure(int)
     * #onFailure(String)
     */
}
