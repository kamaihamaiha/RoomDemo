package cn.kk.roomdemo.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import cn.kk.roomdemo.R;
import cn.kk.roomdemo.db.Word;
import cn.kk.roomdemo.viewmodel.WordViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private Button btnAddConfirm;
    private EditText etEnglish,etChinese;
    private FragmentActivity fragmentActivity;
    private WordViewModel wordViewModel;
    private InputMethodManager imm;


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentActivity = requireActivity();

        wordViewModel = new ViewModelProvider(fragmentActivity).get(WordViewModel.class);

        etEnglish = fragmentActivity.findViewById(R.id.et_english);
        etChinese = fragmentActivity.findViewById(R.id.et_chinese);
        btnAddConfirm = fragmentActivity.findViewById(R.id.btn_add_confirm);

        //按钮默认是灰色的，不能点击
        btnAddConfirm.setEnabled(false);

        //将键盘光标聚焦在 etEnglish 控件上
        etEnglish.requestFocus();
        imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEnglish,0);

        //给 etEnglish 和 etChinese 增加内容监听器
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputEnglish = etEnglish.getText().toString().trim();
                String inputChinese = etChinese.getText().toString().trim();

                btnAddConfirm.setEnabled(!inputEnglish.isEmpty() && !inputChinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etEnglish.addTextChangedListener(textWatcher);
        etChinese.addTextChangedListener(textWatcher);

        //保存单词
        btnAddConfirm.setOnClickListener(v -> {
            String inputEnglish = etEnglish.getText().toString().trim();
            String inputChinese = etChinese.getText().toString().trim();


            Word word = new Word(wordViewModel.getLastWordId() + 1,inputEnglish,inputChinese);
            wordViewModel.insertWord(word);

            //导航，回到 addFragment
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();

            //隐藏键盘
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
    }
}
