package cn.kk.roomdemo.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.db.WordRepository;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository wordRepository;
    private final LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        allWords = wordRepository.getAllWords();
    }

   public   LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insertWord(Word... words){
        wordRepository.insertWord(words);
    }

    public void deleteWord(Word... words){
        wordRepository.deleteWord(words);
    }

    public void deleteAllWord(){
        wordRepository.deleteAllWord();
    }

    public void updateWord(Word... words){
        wordRepository.updateWord(words);
    }

    public int getLastWordId(){
       return wordRepository.getLastWordId();
    }

}
