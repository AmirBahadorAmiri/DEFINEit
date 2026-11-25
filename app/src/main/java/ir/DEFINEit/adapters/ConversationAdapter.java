package ir.DEFINEit.adapters;

import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amirbahadoramiri.logger.Logger;
import com.github.amirbahadoramiri.telegramdialog.TelegramColors;
import com.github.amirbahadoramiri.telegramdialog.TelegramPopupMenu;
import com.github.amirbahadoramiri.telegramdialog.direction.DialogDirection;
import com.github.amirbahadoramiri.telegramdialog.listeners.OnMenuListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ir.DEFINEit.R;
import ir.DEFINEit.listener.ResponseListener;
import ir.DEFINEit.models.ConversationModel;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.tts_manager.TTsSingle;
import ir.DEFINEit.tools.volume_manager.VolumeManager;
import okhttp3.Response;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MainHolder> {

    List<ConversationModel> conversationModelList;
    private final OnRetryListener retryListener;

    public interface OnRetryListener {
        void onRetry(int position);
    }

    public ConversationAdapter(List<ConversationModel> conversationModelList, OnRetryListener retryListener) {
        this.conversationModelList = conversationModelList;
        this.retryListener = retryListener;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_from, parent, false));
        else
            return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_to, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty() && "typewriter".equals(payloads.get(0))) {
            holder.bindView(position);
            holder.typeText(conversationModelList.get(position).getText());
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onViewRecycled(@NonNull MainHolder holder) {
        holder.cancelTypewriter();
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return conversationModelList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return conversationModelList.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {

        AppCompatTextView conversation_item_text;
        AppCompatImageView conversation_item_copy, conversation_item_speaker;
        Runnable typeRunnable;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            conversation_item_text = itemView.findViewById(R.id.conversation_item_text);
            conversation_item_copy = itemView.findViewById(R.id.conversation_item_copy);
            conversation_item_speaker = itemView.findViewById(R.id.conversation_item_speaker);
        }

        public void bindView(int position) {
            ConversationModel model = conversationModelList.get(position);
            boolean thinking = model.isThinking();
            conversation_item_text.setText(thinking ? "Thinking..." : model.getText());
            conversation_item_copy.setVisibility(thinking ? View.GONE : View.VISIBLE);
            conversation_item_speaker.setVisibility(thinking ? View.GONE : View.VISIBLE);
            itemView.setOnClickListener(model.isFailed() ? v -> showRetryPopup(v, position) : null);
            conversation_item_copy.setOnClickListener(v -> {
                CopyHelper.initialize(itemView.getContext());
                CopyHelper.insert(conversationModelList.get(position).getText());
            });
            conversation_item_speaker.setOnClickListener(v -> {
                if (VolumeManager.getVolume(itemView.getContext()) == 0) {
                    Snackbar.make(v, "صدای سیستم قطع است", Snackbar.LENGTH_LONG)
                            .setAction("افزایش صدا", n -> VolumeManager.setVolume(itemView.getContext(), VolumeManager.getManager(itemView.getContext()).getStreamMaxVolume(AudioManager.STREAM_MUSIC))).setActionTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_color)).show();
                } else {
                    TTsSingle.initialize(itemView.getContext(), new ResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            if (TTsSingle.isSupportLanguage(itemView.getContext(), conversationModelList.get(position).getLanguageCode())) {
                                TTsSingle.speak(itemView.getContext(), conversationModelList.get(position).getText());
                            } else {
                                Toast.makeText(itemView.getContext(), "بسته نرم افزار صوتی این زبان نصب نشده است", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Logger.debug("ConversationAdapter", "tts manager failure");
                        }
                    });
                }
            });
        }

        void showRetryPopup(View anchor, int position) {
            new TelegramPopupMenu(anchor.getContext(), anchor)
                    .setCardRadius(16)
                    .setWidth(180)
                    .setDirection(DialogDirection.LTR)
                    .setCardBackgroundColor(ContextCompat.getColor(anchor.getContext(), R.color.on_page_background))
                    .setItemTextColor(TelegramColors.getTextColor(anchor.getContext()))
                    .setItemIconTint(ContextCompat.getColor(anchor.getContext(), R.color.primary_color))
                    .addItem(R.drawable.ic_reverse, "Retry")
                    .setOnClickListener(new OnMenuListener() {
                        @Override
                        public void onItemSelected(int itemPosition, String itemText) {
                            retryListener.onRetry(position);
                        }
                    })
                    .show();
        }

        void typeText(String text) {
            cancelTypewriter();
            final int targetPosition = getBindingAdapterPosition();
            if (targetPosition == RecyclerView.NO_POSITION) {
                conversation_item_text.setText(text);
                return;
            }
            conversation_item_text.setText("");
            final int delay = Math.max(4, Math.min(30, 900 / Math.max(1, text.length())));
            final int[] index = {0};
            typeRunnable = new Runnable() {
                @Override
                public void run() {
                    if (getBindingAdapterPosition() != targetPosition) return;
                    if (index[0] < text.length()) {
                        conversation_item_text.append(String.valueOf(text.charAt(index[0]++)));
                        itemView.postDelayed(this, delay);
                    }
                }
            };
            itemView.postDelayed(typeRunnable, delay);
        }

        void cancelTypewriter() {
            if (typeRunnable != null) itemView.removeCallbacks(typeRunnable);
        }
    }
}
