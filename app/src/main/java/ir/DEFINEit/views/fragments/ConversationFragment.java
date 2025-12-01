package ir.DEFINEit.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.DEFINEit.R;
import ir.DEFINEit.adapters.ConversationAdapter;
import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.models.ConversationModel;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.translate_manager.TranslateManager;
import ir.DEFINEit.views.activities.ChangeLanguageActivity;
import ir.DEFINEit.views.activities.SettingsActivity;
import okhttp3.Response;

public class ConversationFragment extends Fragment {

    public static ConversationFragment conversationFragment;

    AppCompatImageView activity_conversation_settings_btn, activity_conversation_mic_btn, activity_conversation_reverse_btn, activity_conversation_send_from, activity_conversation_send_to;
    AppCompatEditText activity_conversation_edittext;
    MaterialButton activity_conversation_from_btn, activity_conversation_to_btn;
    RecyclerView activity_conversation_recyclerview;
    List<ConversationModel> conversationModelList = new ArrayList<>();
    ConversationAdapter conversationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setup(view);
    }

    private void findViews(View view) {
        activity_conversation_settings_btn = view.findViewById(R.id.activity_conversation_settings_btn);
        activity_conversation_mic_btn = view.findViewById(R.id.activity_conversation_mic_btn);
        activity_conversation_reverse_btn = view.findViewById(R.id.activity_conversation_reverse_btn);
        activity_conversation_send_from = view.findViewById(R.id.activity_conversation_send_from);
        activity_conversation_send_to = view.findViewById(R.id.activity_conversation_send_to);
        activity_conversation_edittext = view.findViewById(R.id.activity_conversation_edittext);
        activity_conversation_from_btn = view.findViewById(R.id.activity_conversation_from_btn);
        activity_conversation_to_btn = view.findViewById(R.id.activity_conversation_to_btn);
        activity_conversation_recyclerview = view.findViewById(R.id.activity_conversation_recyclerview);
    }

    private void setup(View view) {

        activity_conversation_settings_btn.setOnClickListener(v -> startActivity(new Intent(requireContext(), SettingsActivity.class)));

        activity_conversation_reverse_btn.setOnClickListener(v -> {
            LanguageManager.reverceLanguage(requireContext());
            getLanguages();
        });

        activity_conversation_mic_btn.setOnClickListener(v -> {
            Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageManager.getFromLangaugeCode(requireContext()));
//                voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "fa-IR");
//                voiceIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);

            startActivityForResult(voiceIntent, 1002);
        });

        activity_conversation_to_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", false);
            startActivityForResult(intent, 1003);
        });
        activity_conversation_from_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChangeLanguageActivity.class);
            intent.putExtra("isFrom", true);
            startActivityForResult(intent, 1003);
//            can be change request code to 1004 for change language
        });

        activity_conversation_send_from.setOnClickListener(v -> {

            String text = Objects.requireNonNull(activity_conversation_edittext.getText()).toString();
            String from = LanguageManager.getFromLangaugeCode(requireContext());
            String to = LanguageManager.getToLangaugeCode(requireContext());

            conversationModelList.add(new ConversationModel(text, from, 1));
            conversationAdapter.notifyItemInserted((conversationModelList.size() - 1));
            activity_conversation_recyclerview.scrollToPosition((conversationModelList.size() - 1));

            translate(text, from, to);

        });
        activity_conversation_send_to.setOnClickListener(v -> {

            String text = Objects.requireNonNull(activity_conversation_edittext.getText()).toString();
            String from = LanguageManager.getToLangaugeCode(requireContext());
            String to = LanguageManager.getFromLangaugeCode(requireContext());

            conversationModelList.add(new ConversationModel(text, from, 1));
            conversationAdapter.notifyItemInserted((conversationModelList.size() - 1));
            activity_conversation_recyclerview.scrollToPosition((conversationModelList.size() - 1));

            translate(text, from, to);

        });

        conversationAdapter = new ConversationAdapter(conversationModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setReverseLayout(true); // ترتیب معکوس
        layoutManager.setStackFromEnd(true); // شروع از انتها
        activity_conversation_recyclerview.setLayoutManager(layoutManager);
        activity_conversation_recyclerview.setAdapter(conversationAdapter);
    }

    private void translate(String text, String fromLanguage, String toLanguage) {

        activity_conversation_edittext.setText("");
        if (!text.isEmpty()) {
            ResponseListener responseListener = new ResponseListener() {
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
                            requireActivity().runOnUiThread(() -> {
                                conversationModelList.add(new ConversationModel(translatedText.toString(), toLanguage, 2));
                                conversationAdapter.notifyItemInserted((conversationModelList.size() - 1));
                                activity_conversation_recyclerview.scrollToPosition((conversationModelList.size() - 1));
                            });
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
            };

            TranslateManager.translateByGoogle(requireContext(), text, fromLanguage, toLanguage, responseListener);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getLanguages();
    }

    private void getLanguages() {
        activity_conversation_from_btn.setText(LanguageManager.getFromLangauge(requireContext()));
        activity_conversation_to_btn.setText(LanguageManager.getToLangauge(requireContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == Activity.RESULT_OK && null != data) {
                ArrayList<String> spokenSearch = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (spokenSearch != null) {
                    String grabString = spokenSearch.get(0);
                    if (Objects.requireNonNull(activity_conversation_edittext.getText()).toString().equals(""))
                        activity_conversation_edittext.setText(grabString);
                    else {
                        String str = activity_conversation_edittext.getText() + " " + grabString;
                        activity_conversation_edittext.setText(str);
                    }
                    activity_conversation_edittext.setSelection(activity_conversation_edittext.length());
                }
            }
        } else if (requestCode == 1003 && resultCode == 1003) {
            getLanguages();
        }
    }

    public static ConversationFragment getConversationFragment() {
        if (conversationFragment == null) {
            conversationFragment = new ConversationFragment();
            conversationFragment.setArguments(new Bundle());
        }
        return conversationFragment;
    }
}
