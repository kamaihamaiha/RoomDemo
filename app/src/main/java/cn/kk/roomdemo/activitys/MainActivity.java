package cn.kk.roomdemo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import cn.kk.roomdemo.R;
import cn.kk.roomdemo.adapter.WordAdapter;
import cn.kk.roomdemo.constant.Data;
import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.utils.ToastHelper;
import cn.kk.roomdemo.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    private int index = 0;
    private LiveData<List<Word>> allWords;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

//        binding.setWord(wordViewModel);
//        binding.setLifecycleOwner(this);

        initData();

        recyclerView = findViewById(R.id.recyclerView);
        wordAdapter = new WordAdapter();
        wordAdapter.setmWordViewModel(wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordAdapter);


        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            int remainder = index % Data.tempDataEnglish.length;
            index++;
            Word word = new Word(index, Data.tempDataEnglish[remainder], Data.tempDataChinese[remainder]);
            wordViewModel.insertWord(word);

        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            Word word = new Word();
            index--;
            word.setId(index);
            wordViewModel.deleteWord(word);

        });

        findViewById(R.id.btnModify).setOnClickListener(v -> {
            Word word = new Word();
            word.setId(index - 1);
            word.setChineseMeaning("新");
            wordViewModel.updateWord(word);

        });
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            wordViewModel.deleteAllWord();
            index = 0;
        });

    }

    private void initData() {
        allWords = wordViewModel.getAllWords();
        index = wordViewModel.getLastWordId() + 1;
        allWords.observe(this, words -> {

            wordAdapter.setAllWordList(words);
            wordAdapter.notifyDataSetChanged();

            ToastHelper.showShort("当前 index: " + index);
        });


        ToastHelper.getInstance(getApplicationContext());
    }


}
