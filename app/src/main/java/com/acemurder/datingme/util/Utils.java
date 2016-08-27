package com.acemurder.datingme.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.User;
import com.alibaba.fastjson.util.UTF8Decoder;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by zhengyuxuan on 16/8/25.
 */

public class Utils {
    private Utils() {

    }

    public static View hideSoftInput(final View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null)
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return view;
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageManager mPackageManager = context.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(context.getPackageName(), 0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static void showSnackbar(View view,String data){
        Snackbar sBar = Snackbar.make(view, data, Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout)sBar.getView();
        ve.setBackgroundColor(Color.parseColor("#DEAE75"));
        ve.setAlpha(0.5f);
        ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        sBar.show();
    }
}
