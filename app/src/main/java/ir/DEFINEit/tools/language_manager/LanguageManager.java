package ir.DEFINEit.tools.language_manager;

import android.content.Context;

import ir.DEFINEit.AppManager;
import ir.DEFINEit.tools.country.Country;

public class LanguageManager {

    public static final String TRANSLATE_TO = "TRANSLATE_TO";

    public static final String DEFAULT_VOICE_PACK = "DEFAULT_VOICE_PACK";

    public static final String TRANSLATE_FROM = "TRANSLATE_FROM";

    public static void setDefaultVoiceLanguage(Context context, String locale) {
        AppManager.qshare.writeString(DEFAULT_VOICE_PACK, locale);
    }

    public static String getDefaultVoiceLanguage(Context context) {
        if (AppManager.qshare.readString(DEFAULT_VOICE_PACK).isEmpty())
            setDefaultVoiceLanguage(context, "en-US");
        return AppManager.qshare.readString(DEFAULT_VOICE_PACK);
    }

    public static String getFromLangauge(Context context) {
        if (AppManager.qshare.readInt(TRANSLATE_FROM, -1) == -1)
            setFromLangauge(context, 0);
        return Country.generateLanguage().get(AppManager.qshare.readInt(TRANSLATE_FROM, -1) - 1).getName();
    }

    public static String getToLangauge(Context context) {
        if (AppManager.qshare.readInt(TRANSLATE_TO, -1) == -1)
            setToLangauge(context, 1);
        return Country.generateLanguage().get(AppManager.qshare.readInt(TRANSLATE_TO, -1) - 1).getName();
    }

    public static String getFromLangaugeCode(Context context) {
        if (AppManager.qshare.readInt(TRANSLATE_FROM, -1) == -1)
            setFromLangauge(context, 0);
        return Country.generateLanguage().get(AppManager.qshare.readInt(TRANSLATE_FROM, -1) - 1).getCode();
    }

    public static String getToLangaugeCode(Context context) {
        if (AppManager.qshare.readInt(TRANSLATE_TO, -1) == -1)
            setToLangauge(context, 1);
        return Country.generateLanguage().get(AppManager.qshare.readInt(TRANSLATE_TO, -1) - 1).getCode();
    }

    public static void reverceLanguage(Context context) {
        int to = AppManager.qshare.readInt(TRANSLATE_TO, -1) - 1;
        int from = AppManager.qshare.readInt(TRANSLATE_FROM, -1) - 1;

        setFromLangauge(context, to);
        setToLangauge(context, from);
    }

    public static void setFromLangauge(Context context, int pos) {
        AppManager.qshare.writeInt(TRANSLATE_FROM, pos + 1);
    }

    public static void setToLangauge(Context context, int pos) {
        AppManager.qshare.writeInt(TRANSLATE_TO, pos + 1);
    }

}
