package com.acemurder.datingme.modules.dating;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
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
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.acemurder.datingme.modules.im.guide.event.LeftChatItemClickEvent;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.modules.me.MyDateActivity;
import com.acemurder.datingme.util.TimeUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.ThreadMode;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.acemurder.datingme.R.id.dating_item_tv_name;


/**
 * Created by fg on 2016/8/16.
 */
public class DatingAdapter extends RecyclerView.Adapter<DatingAdapter.DatingViewHolder> {

    List<DatingItem> mDatingItemList;
    Context mContext;
    private boolean isFromDatingFragment = true;

    public DatingAdapter(List<DatingItem> datingItems, Context context) {
        mDatingItemList = datingItems;
        mContext = context;
    }

    public DatingAdapter(List<DatingItem> datingItems, Context context, boolean isFromDatingFragment) {
        mDatingItemList = datingItems;
        mContext = context;
        this.isFromDatingFragment = isFromDatingFragment;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public DatingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);

        return new DatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DatingViewHolder holder, int position) {
        holder.setDatingItem(mDatingItemList.get(position));

        holder.mPhotoImage.setVisibility(View.VISIBLE);
        holder.mIsDateImage.setVisibility(View.GONE);
        holder.mChatText.setTextColor(Color.parseColor("#DEAE75"));
        holder.mDateText.setTextColor(Color.parseColor("#DEAE75"));
        if (mDatingItemList.get(position).hasDated()) {
            holder.mIsDateImage.setVisibility(View.VISIBLE);

            if (mDatingItemList.get(position).getReceiver().equals(APP.getAVUser().getUsername())) {
                holder.mChatText.setTextColor(Color.RED);
                holder.mDateText.setTextColor(Color.RED);
            } else {
                holder.mChatText.setTextColor(Color.GRAY);
                holder.mDateText.setTextColor(Color.GRAY);
            }

            //   holder.mChatText.setClickable(false);
            //   holder.mDateText.setClickable(false);
        }
        holder.mNameText.setText(mDatingItemList.get(position).getPromulgator());
        holder.mContentText.setText(mDatingItemList.get(position).getContent());
        holder.mThemeText.setText("#" + mDatingItemList.get(position).getTheme() + "#");
        holder.mTimeText.setText(TimeUtils.getTimeDetail(mDatingItemList.get(position).getCreatedAt()
                .replace("T", " ").substring(0, 19)));
        if (!mDatingItemList.get(position).getPromulgatorPhoto().equals("null") &&
                mDatingItemList.get(position).getPromulgatorPhoto().startsWith("http"))
            Glide.with(mContext).load(mDatingItemList.get(position).getPromulgatorPhoto()).asBitmap().centerCrop().into(holder.mCircleImageView);

        if (!mDatingItemList.get(position).getPhotoSrc().equals("null") && !mDatingItemList.get(position).getPhotoSrc().isEmpty()) {
            Log.e("onBindViewHolder", position + "     " + mDatingItemList.get(position).getPhotoSrc());
            Glide.with(mContext).load(mDatingItemList.get(position).getPhotoSrc()).centerCrop().into(holder.mPhotoImage);
        } else
            holder.mPhotoImage.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return mDatingItemList.size();
    }


    class DatingViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(dating_item_tv_name)
        TextView mNameText;
        @BindView(R.id.dating_item_tv_time)
        TextView mTimeText;
        @BindView(R.id.dating_item_tv_chat)
        TextView mChatText;
        @BindView(R.id.dating_item_iv_is_date)
        ImageView mIsDateImage;
        @BindView(R.id.dating_item_tv_date)
        TextView mDateText;

        public void setDatingItem(DatingItem datingItem) {
            mDatingItem = datingItem;
        }

        private DatingItem mDatingItem;


        public DatingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.item_dating_cardview_card)
        public void onCardClick() {

        }

        @OnClick(R.id.dating_item_iv_pic)
        public void onImageClick() {
            Intent i = new Intent(itemView.getContext(), ImageActivity.class);
            i.putExtra("url", mDatingItem.getPhotoSrc());
            itemView.getContext().startActivity(i);

        }

        @OnClick({R.id.dating_item_civ_photo, R.id.dating_item_tv_name})
        public void onPersonClick() {
            if (isFromDatingFragment) {
                if (mDatingItem.getPromulgator().equals(APP.getAVUser().getUsername())) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), MyDateActivity.class));

                } else {
                    Intent intent = new Intent(itemView.getContext(), PersonDatingActivity.class);
                    intent.putExtra("name", mDatingItem.getPromulgator());
                    itemView.getContext().startActivity(intent);
                }
            }

        }


        @OnClick({R.id.dating_item_tv_chat, R.id.dating_item_tv_date})
        public void onChatClick(View view) {
            /*Intent intent = new Intent(mChatText.getContext(), LCIMConversationActivity.class);
            intent.putExtra(LCIMConstants.PEER_ID, mDatingItem.getObjectId());
            mChatText.getContext().startActivity(intent);*/
           /* LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
            clickEvent.userId = mDatingItem.getPromulgator();
            EventBus.getDefault().post(clickEvent);*/
            if (mDatingItem.hasDated() && !mDatingItem.getReceiver().equals(APP.getAVUser().getUsername())) {

                return;
            }
            if (mDatingItem.getPromulgator().equals(APP.getAVUser().getUsername())) {
                Snackbar.make(itemView, "您要和自己约吗?找不到人?去广场逛逛吧", Snackbar.LENGTH_SHORT).show();
                return;
            }

            switch (view.getId()) {
                case R.id.dating_item_tv_chat:
                    Intent intent = new Intent(itemView.getContext(), AVSingleChatActivity.class);
                    intent.putExtra(Constants.MEMBER_ID, mDatingItem.getPromulgator());
                    itemView.getContext().startActivity(intent);
                    break;
                case R.id.dating_item_tv_date:
                    RequestManager.INSTANCE.date(new SimpleSubscriber<Response>(itemView.getContext(), true, false, new SubscriberListener<Response>() {
                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            Snackbar.make(itemView, "遇到点小错误,稍后再试", Snackbar.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNext(Response response) {
                            super.onNext(response);
                            mDatingItem.setReceiver(APP.getAVUser().getUsername());
                            mDatingItem.setHasDated(true);
                            DatingAdapter.this.notifyDataSetChanged();
                        }
                    }), mDatingItem);

            }

        }

    }
}
