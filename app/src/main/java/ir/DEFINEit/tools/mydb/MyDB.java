package ir.DEFINEit.tools.mydb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ir.DEFINEit.models.WordModel;

@Database(entities = {WordModel.class}, version = 3, exportSchema = false)
public abstract class MyDB extends RoomDatabase {

    private static MyDB myDB;

    public static MyDB getInstance(Context context) {
        if (myDB == null)
            myDB = Room.databaseBuilder(context, MyDB.class, "words")
                    .createFromAsset("database/words")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                        }
                    }, new Migration(2, 3) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("DROP TABLE IF EXISTS text_tb");
                            database.execSQL("CREATE TABLE word_tb_new (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `persian_word` TEXT, `english_word` TEXT)");
                            database.execSQL("INSERT INTO word_tb_new (`id`, `persian_word`, `english_word`) SELECT `id`, `persian_word`, `english_word` FROM word_tb");
                            database.execSQL("DROP TABLE word_tb");
                            database.execSQL("ALTER TABLE word_tb_new RENAME TO word_tb");
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        return myDB;
    }

    public abstract WordDao getWordDao();

}
