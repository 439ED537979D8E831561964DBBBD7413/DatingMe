package com.acemurder.datingme.modules.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.acemurder.datingme.util.TimeUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengyuxuan on 16/8/25.
 */

public class MyDateAdapter extends RecyclerView.Adapter<MyDateAdapter.MyDateViewHolder>{
    private Context mContext;
    private List<DatingItem> mDatingItemList;
    private boolean isFromMy = true;


    public MyDateAdapter(Context context, List<DatingItem> datingItems) {
        mContext = context;
        mDatingItemList = datingItems;
    }

    public MyDateAdapter(Context context, List<DatingItem> datingItems,boolean isFromMy) {
        mContext = context;
        mDatingItemList = datingItems;
        this.isFromMy = isFromMy;
    }

    @Override
    public MyDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_date, parent, false);
        return new MyDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyDateViewHolder holder, int position) {
       // holder.setDatingItem(mDatingItemList.get(position));
        holder.setDatingItem(mDatingItemList.get(position));
        holder.mPhotoImage.setVisibility(View.VISIBLE);
        holder.mIsDateImage.setVisibility(View.GONE);
      //  holder.mChatText.setTextColor(Color.parseColor("#DEAE75"));
       // holder.mDateText.setTextColor(Color.parseColor("#DEAE75"));
        if (mDatingItemList.get(position).hasDated()) {
            holder.mIsDateImage.setVisibility(View.VISIBLE);



            //   holder.mChatText.setClickable(false);
            //   holder.mDateText.setClickable(false);
        }
        holder.mNameText.setText(isFromMy ? "我" : mDatingItemList.get(position).getPromulgator());
        holder.mContentText.setText(mDatingItemList.get(position).getContent());
        holder.mThemeText.setText("#" + mDatingItemList.get(position).getTheme() + "#");
        holder.mTimeText.setText(TimeUtils.getTimeDetail(mDatingItemList.get(position).getCreatedAt()
                .replace("T", " ").substring(0, 19)));

        if (!mDatingItemList.get(position).getPromulgatorPhoto().equals("null")){
            Log.e("MyDate_onBindViewHolder",mDatingItemList.get(position).getPromulgatorPhoto());
            Glide.with(APP.getContext()).load(mDatingItemList.get(position).getPromulgatorPhoto()).asBitmap().centerCrop().into(holder.mCircleImageView);

        }

        if (!mDatingItemList.get(position).getPhotoSrc().equals("null") && !mDatingItemList.get(position).getPhotoSrc().isEmpty()) {
            Log.e("onBindViewHolder", position + "     " + mDatingItemList.get(position).getPhotoSrc());
            Glide.with(mContext).load(mDatingItemList.get(position).getPhotoSrc()).asBitmap().centerCrop().into(holder.mPhotoImage);
        } else
            holder.mPhotoImage.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mDatingItemList.size();
    }

    class MyDateViewHolder extends RecyclerView.ViewHolder{

        private DatingItem mDatingItem;

        public void setDatingItem(DatingItem datingItem) {
            mDatingItem = datingItem;
        }

        @BindView(R.id.item_dating_cardview_card)
        CardView cardView;
        @BindView(R.id.dating_item_civ_photo)
        CircleImageView mCircleImageView;
        @BindView(R.id.dating_item_iv_pic)
        ImageView mPhotoImage;
        @BindView(R.id.dating_item_tv_theme)
        TextView mThemeText;
        @BindView(R.id.dating_item_tv_content)
        TextView mContentText;
        @BindView(R.id.dating_item_tv_name)
        TextView mNameText;
        @BindView(R.id.dating_item_tv_time)
        TextView mTimeText;
        @BindView(R.id.dating_item_iv_is_date)
        ImageView mIsDateImage;


        public MyDateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.item_dating_cardview_card)
        public void OnCardClick(){
            if (!isFromMy){
                Intent intent = new Intent(itemView.getContext(), AVSingleChatActivity.class);
                intent.putExtra(Constants.MEMBER_ID, mDatingItem.getPromulgator());
                itemView.getContext().startActivity(intent);
            }
        }


    }
}
