package ir.DEFINEit.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.models.TextModel;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.mydb.MyDB;
import ir.DEFINEit.tools.translate_manager.TranslateManager;
import ir.DEFINEit.tools.tts_manager.TTsSingle;
import ir.DEFINEit.tools.volume_manager.VolumeManager;
import ir.DEFINEit.views.activities.ChangeLanguageActivity;
import ir.DEFINEit.views.activities.SettingsActivity;
import ir.DEFINEit.views.activities.TranslateHistoryActivity;
import okhttp3.Response;

public class TranslateFragment extends Fragment {

    public static TranslateFragment translateFragment;

    AppCompatImageView fragment_translate_settings_icon, fragment_translate_history_icon, fragment_translate_reverse_icon, fragment_translate_speaker_btn, fragment_translate_copy_btn, fragment_translate_mic_btn;
    AppCompatTextView fragment_translate_textview;
    AppCompatEditText fragment_translate_edittext;
    MaterialButton fragment_translate_to_btn, fragment_translate_from_btn;

    private Observable typingObservable;
    private Disposable typingDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setup(view);
    }

    private void findViews(View view) {
        fragment_translate_settings_icon = view.findViewById(R.id.fragment_translate_settings_icon);
        fragment_translate_history_icon = view.findViewById(R.id.fragment_translate_history_icon);
        fragment_translate_reverse_icon = view.findViewById(R.id.fragment_translate_reverse_icon);
        fragment_translate_speaker_btn = view.findViewById(R.id.fragment_translate_speaker_btn);
        fragment_translate_copy_btn = view.findViewById(R.id.fragment_translate_copy_btn);
        fragment_translate_mic_btn = view.findViewById(R.id.fragment_translate_mic_btn);
        fragment_translate_textview = view.findViewById(R.id.fragment_translate_textview);
        fragment_translate_edittext = view.findViewById(R.id.fragment_translate_edittext);
        fragment_translate_to_btn = view.findViewById(R.id.fragment_translate_to_btn);
        fragment_translate_from_btn = view.findViewById(R.id.fragment_translate_from_btn);
    }

    private void setup(View view) {

        fragment_translate_settings_icon.setOnClickListener(v -> startActivity(new Intent(requireContext(), SettingsActivity.class)));
        fragment_translate_history_icon.setOnClickListener(v -> startActivity(new Intent(requireContext(), TranslateHistoryActivity.class)));
        fragment_translate_speaker_btn.setOnClickListener(v -> {
            if (!fragment_translate_textview.getText().toString().isEmpty()) {
                if (VolumeManager.getVolume(requireContext()) == 0) {
                    Snackbar.make(v, "صدای سیستم قطع است", Snackbar.LENGTH_LONG)
                            .setAction("افزایش صدا", n -> VolumeManager.setVolume(requireContext(), VolumeManager.getManager(requireContext()).getStreamMaxVolume(AudioManager.STREAM_MUSIC))).setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blueColor)).show();
                } else {
                    TTsSingle.initialize(requireContext(), new ResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            if (TTsSingle.isSupportLanguage(requireContext(), LanguageManager.getToLangaugeCode(requireContext()))) {
                                TTsSingle.speak(requireContext(), fragment_translate_textview.getText().toString());
                            } else {
                                Toast.makeText(requireContext(), "بسته نرم افزار صوتی این زبان نصب نشده است", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.d("TAG", "tts manager failure");
                        }
                    });
                }
            }
        });
        fragment_translate_reverse_icon.setOnClickListener(v -> {
            LanguageManager.reverceLanguage(requireContext());
            getLanguages();
            translate();
        });

        fragment_translate_copy_btn.setOnClickListener(v -> {
            if (!fragment_translate_textview.getText().toString().isEmpty()) {
                CopyHelper.initialize(requireContext());
                CopyHelper.insert(fragment_translate_textview.getText().toString());
            }
        });

//        fragment_translate_mic_btn.setOnClickListener(v -> {
//            Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
////                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageManager.getFromLangaugeCode(requireContext()));
////                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "fa-IR");
////                voiceIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
//
//            startActivityForResult(voiceIntent, 1002);
//        });

        fragment_translate_to_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", false);
            startActivityForResult(intent, 1003);
        });
        fragment_translate_from_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", true);
            startActivityForResult(intent, 1003);
//            can be change request code to 1004 for change language
        });

        fragment_translate_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = Objects.requireNonNull(editable.toString());
                if (typingDisposable != null && !typingDisposable.isDisposed())
                    typingDisposable.dispose();
                typingObservable = Observable.timer(1500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                if (text.isEmpty()) {
                    fragment_translate_textview.setText("");
                } else {
                    typingDisposable = typingObservable
                            .subscribe(o -> translate());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    private void getLanguages() {
        fragment_translate_from_btn.setText(LanguageManager.getFromLangauge(requireContext()));
        fragment_translate_to_btn.setText(LanguageManager.getToLangauge(requireContext()));
    }

    private void translate() {
        if (!Objects.requireNonNull(fragment_translate_edittext.getText()).toString().isEmpty()) {

            String text = Objects.requireNonNull(fragment_translate_edittext.getText()).toString();

            TranslateManager.translateByGoogle(requireContext(), text, LanguageManager.getFromLangaugeCode(requireContext()), LanguageManager.getToLangaugeCode(requireContext()), new ResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    if (response.isSuccessful()) {
                        try {
                            String body = Objects.requireNonNull(response.body()).string();

                            JSONArray array = new JSONArray(body);
                            JSONArray array_0 = array.getJSONArray(0);
                            StringBuilder translatedText = new StringBuilder();
                            for (int x = 0; x < array_0.length(); x++) {
                                JSONArray dynamic_object = array_0.getJSONArray(x);
                                if (!dynamic_object.isNull(0)) {
                                    translatedText.append(dynamic_object.getString(0));
                                }
                            }
                            if (fragment_translate_edittext.getText().toString().isEmpty()) {
                                requireActivity().runOnUiThread(() -> fragment_translate_textview.setText(""));
                            } else {
                                requireActivity().runOnUiThread(() -> fragment_translate_textview.setText(translatedText.toString()));
                            }


                            TextModel textModel = new TextModel();
                            textModel.setText(text);
                            textModel.setTranslation(translatedText.toString());
                            textModel.setTranslationTime(System.currentTimeMillis());
                            textModel.setFromLanguageCode(LanguageManager.getFromLangaugeCode(requireContext()));
                            textModel.setToLanguageCode(LanguageManager.getToLangaugeCode(requireContext()));

                            MyDB.getInstance(requireActivity()).getTextDao()
                                    .insert(textModel)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
//                fragment_translate_scrollview.setVisibility(View.INVISIBLE);
//                fragment_translate_copy_btn.setVisibility(View.INVISIBLE);
//                share_text.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "متاسفانه مشکلی در برقراری ارتباط پیش آمد", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLanguages();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (typingDisposable != null && !typingDisposable.isDisposed())
            typingDisposable.dispose();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                ArrayList<String> spokenSearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (spokenSearch != null) {
                    String grabString = spokenSearch.get(0);
                    if (Objects.requireNonNull(fragment_translate_edittext.getText()).toString().equals(""))
                        fragment_translate_edittext.setText(grabString);
                    else {
                        String str = fragment_translate_edittext.getText() + " " + grabString;
                        fragment_translate_edittext.setText(str);
                    }
                    fragment_translate_edittext.setSelection(fragment_translate_edittext.length());
                }
            }
        } else if (requestCode == 1003 && resultCode == 1003) {
            getLanguages();
            translate();
        }
    }

    public static TranslateFragment getTranslateFragment() {
        if (translateFragment == null) {
            translateFragment = new TranslateFragment();
            translateFragment.setArguments(new Bundle());
        }
        return translateFragment;
    }
}
