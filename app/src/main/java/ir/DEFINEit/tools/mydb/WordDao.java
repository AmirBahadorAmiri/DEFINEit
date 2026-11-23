package ir.DEFINEit.tools.mydb;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ir.DEFINEit.models.WordModel;

@Dao
public interface WordDao {

    @Query("SELECT * FROM word_tb WHERE english_word like '%' || :text || '%' ORDER BY :text ASC")
    Single<List<WordModel>> readEnglishWord(String text);

    @Query("SELECT * FROM word_tb WHERE persian_word like '%' || :text || '%' ORDER BY :text DESC")
    Single<List<WordModel>> readPersianWord(String text);

}
