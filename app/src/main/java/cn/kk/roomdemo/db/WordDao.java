package cn.kk.roomdemo.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //Database access object
public interface WordDao {

    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("delete from word")
    void deleteAllWords();

    @Query("select * from word order by id desc")
    LiveData<List<Word>> getAllWords();

    @Query("select * from word where english_word like :params order by id desc")
    LiveData<List<Word>> findWordWithParams(String params);

    @Query("select * from word order by id desc limit 1")
    Word getLastWord();
}
