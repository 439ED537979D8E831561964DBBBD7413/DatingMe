package com.acemurder.datingme.modules.community;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.Remark;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements CommunityContract.IRemarkView{
    private DetailsAdapter mDetailsAdapter;
    private List<Remark>mRemarkList = new ArrayList<>();
    private RemarkPresenter mRemarkPresenter;
    private String objectId;

    @BindView(R.id.activity_details_portrait)CircleImageView mCircleImageView;
    @BindView(R.id.activity_details_name)TextView nameView;
    @BindView(R.id.activity_details_date)TextView dateView;
    @BindView(R.id.details_tv_title)TextView titleView;
    @BindView(R.id.details_tv_content)TextView contentView;
    @BindView(R.id.activity_details_picture) ImageView pictureView;
    @BindView(R.id.details_recycler_view)RecyclerView mRecyclerView;
    @BindView(R.id.activity_details_edit_view)EditText mEditText;
    @BindView(R.id.activity_details_button)ImageButton mImageButton;
    @BindView(R.id.toolbar)Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        AVUser avUser = new AVUser();
        avUser.setUsername("admin");
        avUser.setObjectId("123456");
        APP.setUser(avUser);
        mRemarkPresenter = new RemarkPresenter(this,this);

        initView();
    }

    private void initView() {
        Community community = (Community) getIntent().getSerializableExtra("community_data");
        objectId = community.getObjectId();
        mRemarkPresenter.getRemarkItem(objectId);
        nameView.setText(community.getAuthorName());
        dateView.setText(community.getUpdatedAt());
        titleView.setText(community.getTitle());
        contentView.setText(community.getContent());

        mToolbar.setTitle("动态详情");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailsAdapter = new DetailsAdapter(this,mRemarkList);
        mRecyclerView.setAdapter(mDetailsAdapter);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Remark remark = new Remark();
                remark.setContent(mEditText.getText().toString());
                remark.setAuthorId(APP.getAVUser().getObjectId());
                remark.setAuthorName(APP.getAVUser().getUsername());
                mRemarkPresenter.sendRemarkItem(remark);
            }
        });

    }

    @Override
    public void showRemarkItems(List<Remark> remarks) {
        mRemarkList.addAll(remarks);
        mDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetRemarkItemsError() {
        Toast.makeText(DetailsActivity.this, "抱歉，加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendError() {
        Toast.makeText(DetailsActivity.this, "抱歉，评论失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendSuccess() {
        mRemarkPresenter.getRemarkItem(objectId);
        mDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(CommunityContract.IRemarkPresenter presenter) {

    }
}
