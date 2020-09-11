package cn.kk.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    private int index = 0;
    private LiveData<List<Word>> allWords;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

//        binding.setWord(wordViewModel);
//        binding.setLifecycleOwner(this);

        initData();

        tvData = findViewById(R.id.tvData);
        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            Word word = new Word(index, "w_" + index, "我_" + index);
            wordViewModel.insertWord(word);

            index++;
        });
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            Word word = new Word();
            word.setId(index);
            wordViewModel.deleteWord(word);

            index--;
        });
        findViewById(R.id.btnModify).setOnClickListener(v -> {
            Word word = new Word();
            word.setId(index);
            word.setChineseMeaning("吾_");
            wordViewModel.updateWord(word);

        });
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            wordViewModel.deleteAllWord();
            index = 0;
        });


    }

    private void initData() {
        allWords = wordViewModel.getAllWords();
        allWords.observe(this, words -> {
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
        });
    }


}
