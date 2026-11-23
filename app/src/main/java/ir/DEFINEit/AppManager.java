package ir.DEFINEit;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.amirbahadoramiri.logger.Logger;
import com.github.amirbahadoramiri.qshare.QShare;

import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.tts_manager.TTsSingle;
import okhttp3.Response;

public class AppManager extends Application {

    public static final String DARK_THEME = "dark_theme";
    public static QShare qshare;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setEnabled(BuildConfig.DEBUG);
        qshare = new QShare(this, "app_prefs");
        AppCompatDelegate.setDefaultNightMode(qshare.readBoolean(DARK_THEME, false)
                ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        setupTTS();
        CopyHelper.initialize(this);
    }

    private void setupTTS() {
        TTsSingle.initialize(getApplicationContext(), new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                TTsSingle.isSupportLanguage(getApplicationContext(), LanguageManager.getDefaultVoiceLanguage(getBaseContext()));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Logger.error("AppManager", "tts initialization failed", throwable);
            }
        });
    }

}
