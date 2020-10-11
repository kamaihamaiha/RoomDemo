package cn.kk.roomdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.kk.roomdemo.R;
import cn.kk.roomdemo.viewmodel.WordViewModel;
import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.utils.ToastHelper;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> allWordList = new ArrayList<>();
    private WordViewModel mWordViewModel;

    public void setAllWordList(List<Word> allWordList) {
        this.allWordList = allWordList;
    }


    public void setmWordViewModel(WordViewModel mWordViewModel) {
        this.mWordViewModel = mWordViewModel;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item_view,
                parent, false);

        WordViewHolder wordViewHolder = new WordViewHolder(view);

        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.tvIndex.setText(String.valueOf(position + 1));
        Word word = allWordList.get(position);

//        holder.itemView.setTag(R.id.word_for_view_holder, word);
        holder.tvWordEnglish.setText(word.getWord());
        holder.tvWordChinese.setText(word.getChineseMeaning());

        //避免 holder 因为重复利用，受到其他 position 的影响
        holder.switchChineseInvisible.setOnCheckedChangeListener(null);

        if (word.isChineseInvisible()) {
            holder.switchChineseInvisible.setChecked(true);
            holder.tvWordChinese.setVisibility(View.VISIBLE);
        } else {
            holder.switchChineseInvisible.setChecked(false);
            holder.tvWordChinese.setVisibility(View.GONE);
        }

        //点击条目，显示中文意思
        holder.rootView.setOnClickListener(v -> {
            ToastHelper.showShort(word.getChineseMeaning());
        });

        holder.switchChineseInvisible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            word.setChineseInvisible(isChecked);
            mWordViewModel.updateWord(word);
        });

    }

    @Override
    public int getItemCount() {
        return allWordList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIndex, tvWordEnglish, tvWordChinese;
        private RelativeLayout rootView;
        private Switch switchChineseInvisible;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.root_word_item);
            tvIndex = itemView.findViewById(R.id.tv_word_item_index);
            tvWordEnglish = itemView.findViewById(R.id.tv_word_item_english);
            tvWordChinese = itemView.findViewById(R.id.tv_word_item_chinese);
            switchChineseInvisible = itemView.findViewById(R.id.switchWordItem);
        }

    }
}
