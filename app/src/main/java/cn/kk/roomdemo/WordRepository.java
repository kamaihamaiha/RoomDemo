package cn.kk.roomdemo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private final LiveData<List<Word>> allWords;
    private final WordDao wordDao;

    public WordRepository(Application application) {
        WordDatabase database = WordDatabase.getINSTANCE(application);
        wordDao = database.getWordDao();
        allWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insertWord(Word... words){
        new InsertWordTask(wordDao).execute(words);
    }

    public void deleteWord(Word... words){
        new DeleteWordTask(wordDao).execute(words);
    }

    public void deleteAllWord(){
        new DeleteAllWordTask(wordDao).execute();
    }

    public void updateWord(Word... words){
        new UpdateWordTask(wordDao).execute(words);
    }


    class InsertWordTask extends AsyncTask<Word,Void,Void> {
        WordDao wordDao;
        public InsertWordTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    class DeleteWordTask extends AsyncTask<Word,Void,Void>{
        WordDao wordDao;

        public DeleteWordTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    class UpdateWordTask extends AsyncTask<Word,Void,Void>{
        WordDao wordDao;

        public UpdateWordTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    class DeleteAllWordTask extends AsyncTask<Void,Void,Void>{
        WordDao wordDao;

        public DeleteAllWordTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
