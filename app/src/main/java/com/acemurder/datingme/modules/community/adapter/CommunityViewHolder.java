package com.acemurder.datingme.modules.community.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.modules.community.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengyuxuan on 16/8/25.
 */

public class CommunityViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.community_item_iv_image)
    ImageView imageView;
    @BindView(R.id.item_community_cv_card)
    CardView mCardView;
    @BindView(R.id.community_item_tv_theme)
    TextView mTitleText;
    @BindView(R.id.item_community_tv_time)
    TextView mTimeText;
    @BindView(R.id.item_community_iv_portrait)
    CircleImageView mCircleImageView;
    @BindView(R.id.item_community_tv_name)
    TextView mNameText;
    @BindView(R.id.community_item_tv_content)
    TextView mContentText;

    private Community mCommunity;

    public void setCommunity(Community community) {
        mCommunity = community;
    }

    public Community getCommunity() {
        return mCommunity;
    }

    public CommunityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.item_community_cv_card)
    public void onCardClick(){
        Intent intent = new Intent(mCardView.getContext(),DetailsActivity.class);
        intent.putExtra("community_data",mCommunity);
        mCardView.getContext().startActivity(intent);
    }
}
