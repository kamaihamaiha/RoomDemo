package cn.kk.roomdemo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.kk.roomdemo.R;
import cn.kk.roomdemo.adapter.WordAdapter;
import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.viewmodel.WordViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {

    private WordViewModel wordViewModel;
    private RecyclerView wordsRecyclerView;
    private WordAdapter wordAdapter;

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
        wordsRecyclerView.setAdapter(wordAdapter);

        wordViewModel.getAllWords().observe(requireActivity(), words -> {
            wordAdapter.setAllWordList(words);
            wordAdapter.notifyDataSetChanged();
        });
    }
}
