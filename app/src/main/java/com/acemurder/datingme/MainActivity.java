package com.acemurder.datingme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.acemurder.datingme.bean.DatingItem;
import com.acemurder.datingme.modules.dating.DateModel;
import com.acemurder.datingme.util.LogUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = LogUtils.makeLogTag(this.getClass());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (APP.getAVUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }*/
        DateModel model = new DateModel();
        List<DatingItem> items = model.getDatingItems(0,10);

        if (items != null)
            for (DatingItem item : items){
            LogUtils.LOGE(TAG,item.getContent());
        }else
            LogUtils.LOGE(TAG,"xxxxxxxx");


    }
}
