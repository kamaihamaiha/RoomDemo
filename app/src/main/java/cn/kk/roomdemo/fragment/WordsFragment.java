package cn.kk.roomdemo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private WordViewModel wordViewModel;
    private RecyclerView wordsRecyclerView;
    private WordAdapter wordAdapter;

    private FloatingActionButton addFloatingActionButton;

    public WordsFragment() {
        // Required empty public constructor
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


        wordViewModel.getAllWords().observe(requireActivity(), words -> {
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
