package ir.DEFINEit.models;

public class ConversationModel {
    private String text, languageCode;
    private int viewType;
    private boolean thinking, failed;
    private String sourceText, fromLanguage, toLanguage;

    public ConversationModel() {
    }

    public ConversationModel(String text, String languageCode, int viewType) {
        this.text = text;
        this.languageCode = languageCode;
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isThinking() {
        return thinking;
    }

    public void setThinking(boolean thinking) {
        this.thinking = thinking;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setTranslationRequest(String sourceText, String fromLanguage, String toLanguage) {
        this.sourceText = sourceText;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }
}
