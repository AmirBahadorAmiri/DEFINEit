package ir.DEFINEit.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.github.amirbahadoramiri.telegramdialog.TelegramColors;
import com.github.amirbahadoramiri.telegramdialog.TelegramPopupMenu;
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection;
import com.github.amirbahadoramiri.telegramdialog.listeners.OnMenuListener;
import com.google.android.material.button.MaterialButton;

import ir.DEFINEit.AppManager;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.tts_manager.TTsSingle;

public class SettingsActivity extends BaseActivity {

    AppCompatImageView activity_settings_back_btn;
    MaterialButton activity_settings_theme_btn, activity_settings_voice_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdgeEnable();
        setContentView(R.layout.activity_settings);
        setViewCompat();
        findViews();
        setup();
    }

    private void findViews() {
        activity_settings_back_btn = findViewById(R.id.activity_settings_back_btn);
        activity_settings_theme_btn = findViewById(R.id.activity_settings_theme_btn);
        activity_settings_voice_btn = findViewById(R.id.activity_settings_voice_btn);
    }

    private void setup() {
        activity_settings_back_btn.setOnClickListener(v -> finish());
        activity_settings_theme_btn.setOnClickListener(this::showThemePopup);
        activity_settings_voice_btn.setOnClickListener(this::showVoicePopup);
    }

    private TelegramPopupMenu popup(View anchor) {
        return new TelegramPopupMenu(this, anchor)
                .setCardRadius(16)
                .setWidth(180)
                .setDirection(DialogDirection.LTR)
                .setCardBackgroundColor(ContextCompat.getColor(this, R.color.on_page_background))
                .setItemTextColor(TelegramColors.getTextColor(this))
                .setItemIconTint(ContextCompat.getColor(this, R.color.primary_color));
    }

    private void showThemePopup(View anchor) {
        popup(anchor)
                .addItem(R.drawable.ic_sun, "Light")
                .addItem(R.drawable.ic_moon, "Dark")
                .setOnClickListener(new OnMenuListener() {
                    @Override
                    public void onItemSelected(int position, String text) {
                        boolean dark = position == 1;
                        AppManager.qshare.writeBoolean(AppManager.DARK_THEME, dark);
                        AppCompatDelegate.setDefaultNightMode(dark
                                ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                    }
                })
                .show();
    }

    private void showVoicePopup(View anchor) {
        popup(anchor)
                .addItem(R.drawable.ic_man, "Man")
                .addItem(R.drawable.ic_woman, "Woman")
                .setOnClickListener(new OnMenuListener() {
                    @Override
                    public void onItemSelected(int position, String text) {
                        TTsSingle.setMan(SettingsActivity.this, position == 0);
                        Toast.makeText(SettingsActivity.this, text + " voice selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}
