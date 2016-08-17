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
            holder.nameView.setText(mDatingItemList.get(position).getPromulgator());
            holder.contentView.setText(mDatingItemList.get(position).getContent());
            holder.subjectView.setText(mDatingItemList.get(position).getTheme());
        }

    }

    @Override
    public int getItemCount() {
        return mDatingItemList.size();
    }

    public static class DatingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_dating_cardview_card)CardView cardView;
        @BindView(R.id.item_dating_tv_portrait)CircleImageView mCircleImageView;
        @BindView(R.id.item_dating_tv_name)TextView nameView;
        @BindView(R.id.item_dating_iv_image)ImageView mImageView;
        @BindView(R.id.item_dating_tv_theme)TextView subjectView;
        @BindView(R.id.item_dating_tv_title)TextView titleView;
        @BindView(R.id.item_dating_tv_content)TextView contentView;
        @BindView(R.id.item_dating_iv_share)ImageView shareView;
        @BindView(R.id.item_dating_iv_chat)ImageView chatView;
        @BindView(R.id.item_dating_iv_accept)ImageView acceptView;

        public DatingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
