package ir.DEFINEit.tools.iokhttp;

import androidx.annotation.NonNull;

import java.io.IOException;

import ir.DEFINEit.listener.ResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IOkHttp {

    private OkHttpClient client;
    public static final String TRANSLATE_URL = "https://translate.googleapis.com/translate_a/single";

    public void post(String url, RequestBody body, ResponseListener responseListener) {
        if (client == null)
            client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                responseListener.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    responseListener.onSuccess(response);
                } else {
                    responseListener.onFailure(new Throwable("error code " + response.code()));
                }
            }
        });
    }

    public void get(HttpUrl url, ResponseListener responseListener) {
        if (client == null)
            client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                responseListener.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    responseListener.onSuccess(response);
                } else {
                    responseListener.onFailure(new Throwable("error code " + response.code()));
                }
            }
        });
    }

}
