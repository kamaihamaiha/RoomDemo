package cn.kk.roomdemo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import cn.kk.roomdemo.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //把 fragment 信息导航到 actionBar 上面. step1
        navController = Navigation.findNavController(findViewById(R.id.fragment));
        NavigationUI.setupActionBarWithNavController(this,
                navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //把 fragment 信息导航到 actionBar 上面. step2
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}
