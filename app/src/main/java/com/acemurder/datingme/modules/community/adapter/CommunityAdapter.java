package com.acemurder.datingme.modules.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.modules.community.DetailsActivity;
import com.acemurder.datingme.util.TimeUtils;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fg on 2016/8/19.
 */
public class CommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder> {

    private List<Community> mCommunityItems;
    private Context mContext;

    public CommunityAdapter(Context context, List<Community> communityItem) {
        mContext = context;
        mCommunityItems = communityItem;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_community, parent, false);
        return new CommunityViewHolder(view);
    }



    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        Community c = mCommunityItems.get(position);
        holder.setCommunity(c);
        if (!c.getAuthorPhoto().equals("null")){
            Glide.with(mContext).load(c.getAuthorPhoto()).centerCrop().placeholder(R.drawable.back).into(holder.mCircleImageView);
        }
        if (c.getPhotoSrc() != null && c.getPhotoSrc().size() != 0 && !c.getPhotoSrc().get(0).equals("null")){
            Glide.with(mContext).load(c.getPhotoSrc().get(0)).into(holder.imageView);
        }else{
            Glide.with(mContext).load(R.drawable.back).centerCrop().placeholder(R.drawable.back).into(holder.imageView);
        }
        holder.mNameText.setText(c.getAuthorName());
        holder.mTimeText.setText(TimeUtils.getTimeDetail(c.getUpdatedAt()
                .replace("T"," ").substring(0,19)));
        holder.mTitleText.setText(c.getTitle());
        holder.mContentText.setText(c.getContent());



    }

    @Override
    public int getItemCount() {
        return mCommunityItems.size();
    }

    /*public static class CommunityViewHolder extends RecyclerView.ViewHolder {
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
    }*/


}
