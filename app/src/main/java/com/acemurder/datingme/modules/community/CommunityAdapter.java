package com.acemurder.datingme.modules.community;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
          //  holder.contentView.setText(mCommunityItem.get(position).getContent());
            try {
                Glide.with(mContext).load(mCommunityItem.get(position).getPhotoSrc().get(position))
                        .centerCrop().placeholder(R.drawable.ic_proxy).crossFade().into(holder.imageView);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
        if (itemClickListener != null){
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClickListener.onItemClick(holder.mCardView,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCommunityItem.size();
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.community_iv_image)ImageView imageView;
        @BindView(R.id.community_iv_mark)ImageView markView;
        @BindView(R.id.community_tv_star)TextView starView;
        @BindView(R.id.item_community_cv_card)CardView mCardView;
        public CommunityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
