package cn.kk.roomdemo.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cn.kk.roomdemo.R;
import cn.kk.roomdemo.adapter.WordAdapter;
import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.utils.ToastHelper;
import cn.kk.roomdemo.viewmodel.WordViewModel;

/**
 * 单词页面
 */
public class WordsFragment extends Fragment {
    private static final String TAG = "WordsFragment";
    private WordViewModel wordViewModel;
    private RecyclerView wordsRecyclerView;
    private WordAdapter wordAdapter;

    private FloatingActionButton addFloatingActionButton;
    private LiveData<List<Word>> filterWords;

    public WordsFragment() {
        // Required empty public constructor

        //显示菜单，因为默认是不显示的
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.word_menu,menu);

        //点击 search 按钮，不把左边的标题挤出去
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(750);

        //设置搜索监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ToastHelper.showShort(newText);

                filterWords.removeObservers(requireActivity());
                //模糊查询，数据库
                filterWords = wordViewModel.findWordsWithParams(newText);
                filterWords.observe(requireActivity(), words -> {
                    wordAdapter.setAllWordList(words);
                    wordAdapter.notifyDataSetChanged();
                });
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear_words:
                //菜单-清空操作
                //清空影响太大了，所以要弹出对话框确认
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                        .setTitle("温馨提示")
                        .setMessage("确定要清空吗？")
                        .setPositiveButton("确定",(dialog, which) -> {
                            wordViewModel.deleteAllWord();
                        })
                        .setNegativeButton("取消",(dialog, which) -> {});

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;

            case R.id.menu_item_switch_view:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        wordsRecyclerView = requireActivity().findViewById(R.id.recyclerView);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        wordAdapter = new WordAdapter();
        wordAdapter.setmWordViewModel(wordViewModel);
        wordsRecyclerView.setAdapter(wordAdapter);

        filterWords = wordViewModel.getAllWords();
        filterWords.observe(requireActivity(), words -> {
            wordAdapter.setAllWordList(words);
            wordAdapter.notifyDataSetChanged();
        });

        ToastHelper.getInstance(requireActivity());

        //添加按钮，具有导航功能，指引到 AddFragment 页面
        addFloatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        addFloatingActionButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_wordsFragment_to_addFragment);
        });
    }
}
