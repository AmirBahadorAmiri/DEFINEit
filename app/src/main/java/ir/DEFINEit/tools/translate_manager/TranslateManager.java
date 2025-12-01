package ir.DEFINEit.tools.translate_manager;

import android.content.Context;

import java.util.Objects;

import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.tools.iokhttp.IOkHttp;
import okhttp3.HttpUrl;

public class TranslateManager {

    public static void translateByGoogle(Context context, String text, String fromCode, String toCode, ResponseListener responseListener) {
        IOkHttp iOkHttp = new IOkHttp();
        text = text.replaceAll("۰", "0")
                .replaceAll("۱", "1")
                .replaceAll("۲", "2")
                .replaceAll("۳", "3")
                .replaceAll("۴", "4")
                .replaceAll("۵", "5")
                .replaceAll("۶", "6")
                .replaceAll("۷", "7")
                .replaceAll("۸", "8")
                .replaceAll("۹", "9");
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
