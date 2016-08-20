package com.acemurder.datingme.modules.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.Community;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/19.
 */
public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private List<Community> mCommunityItem;
    private Context mContext;

    public CommunityAdapter(Context context,List<Community> communityItem){
        mContext = context;
        mCommunityItem = communityItem;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_community,parent,false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        if (mCommunityItem != null){
            holder.contentView.setText(mCommunityItem.get(position).getContent());
            Glide.with(mContext).load(mCommunityItem.get(position).getPhotoSrc().get(position))
                    .centerCrop().placeholder(R.id.community_iv_image).crossFade().into(holder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return mCommunityItem.size();
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.community_et_content)EditText contentView;
        @BindView(R.id.community_iv_image)ImageView imageView;
        @BindView(R.id.community_iv_mark)ImageView markView;
        @BindView(R.id.community_tv_star)TextView starView;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
