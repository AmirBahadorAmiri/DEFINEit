package ir.DEFINEit.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_tb")
public class WordModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "persian_word")
    private String persianWord;

    @ColumnInfo(name = "english_word")
    private String englishWord;

    public WordModel(String persianWord, String englishWord) {
        this.persianWord = persianWord;
        this.englishWord = englishWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersianWord() {
        return persianWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setPersianWord(String persianWord) {
        this.persianWord = persianWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

}
