package ir.DEFINEit.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amirbahadoramiri.logger.Logger;
import com.github.amirbahadoramiri.telegramdialog.TelegramColors;
import com.github.amirbahadoramiri.telegramdialog.TelegramPopupMenu;
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection;
import com.github.amirbahadoramiri.telegramdialog.listeners.OnMenuListener;

import java.util.List;

import ir.DEFINEit.R;
import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.models.WordModel;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.tts_manager.TTsSingle;
import okhttp3.Response;

public class DictionaryWordAdapter extends RecyclerView.Adapter<DictionaryWordAdapter.MainHolder> {

    List<WordModel> wordModels;

    public DictionaryWordAdapter(List<WordModel> wordModels) {
        this.wordModels = wordModels;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.onBind(holder);
    }

    @Override
    public int getItemCount() {
        return wordModels.size();
    }

    public void loadData(List<WordModel> newWordModels) {
        wordModels.clear();
        wordModels.addAll(newWordModels);
    }

    private void pronounce(View view, String text) {
        TTsSingle.initialize(view.getContext(), new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                if (TTsSingle.isSupportLanguage(view.getContext(), "en-US")) {
                    TTsSingle.speak(view.getContext(), text);
                } else {
                    Toast.makeText(view.getContext(), "بسته نرم افزار صوتی این زبان نصب نشده است", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Logger.error("DictionaryWordAdapter", "tts manager failure", throwable);
            }
        });
    }

    public class MainHolder extends RecyclerView.ViewHolder {

        AppCompatTextView dictionary_word_item_from, dictionary_word_item_to;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            dictionary_word_item_from = itemView.findViewById(R.id.dictionary_word_item_from);
            dictionary_word_item_to = itemView.findViewById(R.id.dictionary_word_item_to);
        }

        public void onBind(MainHolder holder) {
            WordModel word = wordModels.get(getBindingAdapterPosition());
            holder.dictionary_word_item_from.setText(word.getEnglishWord());
            holder.dictionary_word_item_to.setText(word.getPersianWord());

            holder.itemView.setOnClickListener(v -> {
                String text = word.getEnglishWord();
                new TelegramPopupMenu(v.getContext(), v)
                        .setCardRadius(16)
                        .setWidth(180)
                        .setDirection(DialogDirection.LTR)
                        .setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.on_page_background))
                        .setItemTextColor(TelegramColors.getTextColor(v.getContext()))
                        .setItemIconTint(ContextCompat.getColor(v.getContext(), R.color.primary_color))
                        .addItem(R.drawable.ic_copy, "Copy")
                        .addItem(R.drawable.ic_speaker, "Pronounce")
                        .addItem(R.drawable.ic_send, "Share")
                        .setOnClickListener(new OnMenuListener() {
                            @Override
                            public void onItemSelected(int position, String itemText) {
                                if (position == 0) {
                                    CopyHelper.insert(text);
                                } else if (position == 1) {
                                    pronounce(v, text);
                                } else {
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                                    v.getContext().startActivity(Intent.createChooser(shareIntent, "Share"));
                                }
                            }
                        })
                        .show();
            });
        }

    }

}
