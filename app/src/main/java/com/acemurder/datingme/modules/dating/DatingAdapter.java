package com.acemurder.datingme.modules.dating;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.DatingItem;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/16.
 */
public class DatingAdapter extends RecyclerView.Adapter<DatingAdapter.DatingViewHolder> {

    List<DatingItem>mDatingItemList;
    Context mContext;

    public DatingAdapter(List<DatingItem> datingItems, Context context){
        mDatingItemList = datingItems;
        mContext = context;
    }
    @Override
    public DatingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_date,parent,false);

        return new DatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DatingViewHolder holder, int position) {
        if (mDatingItemList != null){
            holder.mNameText.setText(mDatingItemList.get(position).getPromulgator());
            holder.mContentText.setText(mDatingItemList.get(position).getContent());
            holder.mThemeText.setText("#"+mDatingItemList.get(position).getTheme()+"#");
            if (!mDatingItemList.get(position).getPromulgatorPhoto().equals("null"))
                Glide.with(mContext).load(mDatingItemList.get(position).getPromulgatorPhoto()).into(holder.mCircleImageView);

            if (!mDatingItemList.get(position).getPhotoSrc().equals("null"))
                Glide.with(mContext).load(mDatingItemList.get(position).getPhotoSrc()).into(holder.mPhotoImage);
        }

    }

    @Override
    public int getItemCount() {
        return mDatingItemList.size();
    }

    public static class DatingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_dating_cardview_card)CardView cardView;
        @BindView(R.id.dating_item_civ_photo)CircleImageView mCircleImageView;
        @BindView(R.id.dating_item_iv_pic)ImageView mPhotoImage;
        @BindView(R.id.dating_item_tv_theme)TextView mThemeText;
        @BindView(R.id.dating_item_tv_content)TextView mContentText;
        @BindView(R.id.dating_item_tv_name)TextView mNameText;


        public DatingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
