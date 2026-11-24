package ir.DEFINEit.tools.translate_manager;

import android.content.Context;

import java.util.Objects;

import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.tools.iokhttp.IOkHttp;
import okhttp3.HttpUrl;

public class TranslateManager {

    public static void translateByGoogle(Context context, String text, String fromCode, String toCode, ResponseListener responseListener) {
        IOkHttp iOkHttp = new IOkHttp();
        text = text.replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(IOkHttp.TRANSLATE_URL))
                .newBuilder()
                .addQueryParameter("client", "gtx")
                .addQueryParameter("sl", fromCode)
                .addQueryParameter("tl", toCode)
                .addQueryParameter("dt", "t")
                .addQueryParameter("q", text)
                .addQueryParameter("ie", "UTF-8")
                .addQueryParameter("oe", "UTF-8")
                .build();
        iOkHttp.get(httpUrl, responseListener);
    }
}
