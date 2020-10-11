package cn.kk.roomdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    private static ToastHelper instance;
    private static Context appContext;
    private static Toast mToast;

    public static ToastHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ToastHelper();
        }
        appContext = context;
        return instance;
    }

    public static void showShort(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    public static void showLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        mToast.show();
    }
}
