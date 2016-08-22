package com.acemurder.datingme.modules.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;

import butterknife.BindView;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.activity_details_portrait)CircleImageView mCircleImageView;
    @BindView(R.id.activity_details_name)TextView nameView;
    @BindView(R.id.activity_details_date)TextView dateView;
    @BindView(R.id.details_tv_title)TextView titleView;
    @BindView(R.id.details_tv_content)TextView contentView;
    @BindView(R.id.activity_details_picture) ImageView pictureView;
    @BindView(R.id.details_recycler_view)RecyclerView mRecyclerView;
    @BindView(R.id.activity_details_edit_view)EditText mEditText;
    @BindView(R.id.activity_details_button)ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
