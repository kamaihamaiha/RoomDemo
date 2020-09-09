package cn.kk.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word")
                .allowMainThreadQueries() //强制在工作线程执行
                .build();

        wordDao = wordDatabase.getWordDao();

        tvData = findViewById(R.id.tvData);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnModify).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
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
                refreshData2UI();
                break;
            case R.id.btnDelete:
                Word lastWord = wordDao.getLastWord();
                if (lastWord != null) {
                    wordDao.deleteWords(lastWord);
                }
                refreshData2UI();
                break;
            case R.id.btnModify:
                List<Word> allWords = wordDao.getAllWords();
                if (!allWords.isEmpty()) {
                    for (Word allWord : allWords) {
                        allWord.setWord(allWord.getWord() + "_修改了");
                        wordDao.updateWords(allWord);
                    }
                }
                refreshData2UI();
                break;
            case R.id.btnClear:
                wordDao.deleteAllWords();
                index = 0;
                refreshData2UI();
                break;
        }
    }

    /**
     * 刷新数据到控件上
     */
    private void refreshData2UI() {
        List<Word> allWords = wordDao.getAllWords();
        String data = "";
        if (allWords.isEmpty()) {
            tvData.setText("");
        } else {
            for (Word word : allWords) {
                data += word.getId() + ", " + word.getWord() + ", " + word.getChineseMeaning() + "\n";
            }
            tvData.setText(data);
        }
    }
}
