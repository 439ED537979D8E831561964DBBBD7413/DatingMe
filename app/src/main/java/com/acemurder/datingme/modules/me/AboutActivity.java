package com.acemurder.datingme.modules.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.about_version)
    TextView aboutVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mToolbar.setTitle("关于有约");

        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
        aboutVersion.setText(new StringBuilder("Version ").append(Utils.getAppVersionName(this)));

    }
}
