package cn.kk.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    WordDao wordDao;
    WordDatabase wordDatabase;
    private TextView tvData;
    private int index = 0;
//    private List<Word> allWords;
    private LiveData<List<Word>> liveAllWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordDatabase = WordDatabase.getINSTANCE(this);

        wordDao = wordDatabase.getWordDao();
        liveAllWords = wordDao.getAllWords();

        tvData = findViewById(R.id.tvData);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnModify).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);

        initData();
    }

    private void initData() {
        liveAllWords.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder data = new StringBuilder();
                if (words.isEmpty()) {
                    tvData.setText("");
                } else {
                    for (Word word : words) {
                        data.append(word.getId())
                                .append(", ")
                                .append(word.getWord())
                                .append(", ")
                                .append(word.getChineseMeaning())
                                .append("\n");
                    }
                    tvData.setText(data.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Word word = new Word();
                word.setWord("hello" + "_" + index);
                word.setChineseMeaning("雷猴_" + index);
                wordDao.insertWords(word);
                index++;
//                refreshData2UI();
                break;
            case R.id.btnDelete:
                Word lastWord = wordDao.getLastWord();
                if (lastWord != null) {
                    wordDao.deleteWords(lastWord);
                }
//                refreshData2UI();
                index--;
                break;
            case R.id.btnModify:
                Word wordM = new Word();
                wordM.setWord("he");
                wordM.setChineseMeaning("他");
                wordM.setId(index);

                wordDao.updateWords(wordM);
//                refreshData2UI();
                break;
            case R.id.btnClear:
                wordDao.deleteAllWords();
                index = 0;
//                refreshData2UI();
                break;
        }
    }

    /**
     * 刷新数据到控件上
     */
    private void refreshData2UI() {
        /*allWords = wordDao.getAllWords();
        StringBuilder data = new StringBuilder();
        if (allWords.isEmpty()) {
            tvData.setText("");
        } else {
            for (Word word : allWords) {
                data.append(word.getId())
                        .append(", ")
                        .append(word.getWord())
                        .append(", ")
                        .append(word.getChineseMeaning())
                        .append("\n");
            }
            tvData.setText(data.toString());
        }*/
    }
}
