package ir.DEFINEit.views.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import ir.DEFINEit.R;
import ir.DEFINEit.views.fragments.ConversationFragment;
import ir.DEFINEit.views.fragments.DictionaryFragment;
import ir.DEFINEit.views.fragments.TranslateFragment;

public class MainActivity extends BaseActivity {

    ChipNavigationBar main_activity_bottom_navigation;
    View main_activity_bottom_card;
    private boolean bottomNavigationVisible = true;
    private float bottomNavigationAlpha = 1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdgeEnable();
        setContentView(R.layout.activity_main);
        setViewCompat();
        findViews();
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.main_activity_framelayout);
        int selectedId = R.id.dictionary;
        if (current instanceof TranslateFragment) {
            selectedId = R.id.translate;
        } else if (current instanceof ConversationFragment) {
            selectedId = R.id.conversation;
        }
        setup(selectedId);
        if (current == null) {
            changeFragment(DictionaryFragment.getDictionaryFragment());
        }
    }

    private void findViews() {
        main_activity_bottom_navigation = findViewById(R.id.main_activity_bottom_navigation);
        main_activity_bottom_card = findViewById(R.id.main_activity_bottom_card);
        bottomNavigationAlpha = main_activity_bottom_card.getAlpha();
    }

    public void setBottomNavigationVisible(boolean visible) {
        if (visible == bottomNavigationVisible) return;
        bottomNavigationVisible = visible;
        float hiddenOffset = main_activity_bottom_card.getHeight() + 16 * getResources().getDisplayMetrics().density;
        main_activity_bottom_card.animate()
                .translationY(visible ? 0f : hiddenOffset)
                .alpha(visible ? bottomNavigationAlpha : 0f)
                .setDuration(250)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void setup(int selectedId) {
        main_activity_bottom_navigation.setItemSelected(selectedId, true);
        main_activity_bottom_navigation.setOnItemSelectedListener(id -> {
            if (id == R.id.dictionary) {
                changeFragment(DictionaryFragment.getDictionaryFragment());
            }
            if (id == R.id.translate) {
                changeFragment(TranslateFragment.getTranslateFragment());
            }
            if (id == R.id.conversation) {
                changeFragment(ConversationFragment.getConversationFragment());
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_framelayout, fragment).commit();
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_framelayout, fragment).addToBackStack(null).commit();
    }

}
