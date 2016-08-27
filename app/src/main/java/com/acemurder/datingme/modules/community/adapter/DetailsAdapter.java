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
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.modules.community.DetailsActivity;
import com.acemurder.datingme.modules.dating.ImageActivity;
import com.acemurder.datingme.util.TimeUtils;
import com.bumptech.glide.Glide;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fg on 2016/8/23.
 */
public class DetailsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Remark> mRemarkList;
    private Community mCommunity;
    private final int TYPE_COMMUNITY = 1;
    private final int TYPE_REMARK = 2;

    public DetailsAdapter(Context context, List<Remark> remarkList, Community community) {
        mContext = context;
        mRemarkList = remarkList;
        this.mCommunity = community;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REMARK) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_details, parent, false);
            return new DetailsViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_community, parent, false);
            return new SingleCommunityHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailsViewHolder) {
            if (mRemarkList != null) {
                Glide.with(mContext).load(mRemarkList.get(position-1).getAuthorPhotoSrc()).asBitmap()
                        .centerCrop().into(((DetailsViewHolder) holder).portraitView);
                ((DetailsViewHolder) holder).nameView.setText(mRemarkList.get(position - 1).getAuthorName());
                ((DetailsViewHolder) holder).dateView.setText(TimeUtils.getTimeDetail(mRemarkList.get(position - 1).getUpdatedAt()
                        .replace("T", " ").substring(0, 19)));
                ((DetailsViewHolder) holder).contentView.setText(mRemarkList.get(position - 1).getContent());
            }
        } else {
            Community c = mCommunity;
            ((SingleCommunityHolder) holder).setCommunity(c);
            if (c.getAuthorPhoto() != null && !c.getAuthorPhoto().equals("null")) {
                Glide.with(mContext).load(c.getAuthorPhoto()).asBitmap().centerCrop().into(((SingleCommunityHolder) holder).mCircleImageView);
            }
            if (c.getPhotoSrc() != null && c.getPhotoSrc().size() != 0 && !c.getPhotoSrc().get(0).equals("null")) {
                Glide.with(mContext).load(c.getPhotoSrc().get(0)).into(((SingleCommunityHolder) holder).imageView);
            } else {
                Glide.with(mContext).load(R.drawable.back).centerCrop().placeholder(R.drawable.back).into(((SingleCommunityHolder) holder).imageView);
            }
            ((SingleCommunityHolder) holder).mNameText.setText(c.getAuthorName());
            ((SingleCommunityHolder) holder).mTimeText.setText(TimeUtils.getTimeDetail(c.getUpdatedAt()
                    .replace("T", " ").substring(0, 19)));
            ((SingleCommunityHolder) holder).mTitleText.setText(c.getTitle());
            ((SingleCommunityHolder) holder).mContentText.setText(c.getContent());
        }


    }

    @Override
    public int getItemCount() {
        return mRemarkList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        return position == 0 ? TYPE_COMMUNITY : TYPE_REMARK;
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.details_custom_portrait)
        CircleImageView portraitView;
        @BindView(R.id.item_details_tv_name)
        TextView nameView;
        @BindView(R.id.item_details_tv_date)
        TextView dateView;
        @BindView(R.id.item_details_tv_content)
        TextView contentView;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class SingleCommunityHolder extends CommunityViewHolder {
        public SingleCommunityHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onCardClick() {
            if (getCommunity().getPhotoSrc() != null
                    && getCommunity().getPhotoSrc().size() != 0
                    && !getCommunity().getPhotoSrc().get(0).equals("null")) {
                Intent i = new Intent(itemView.getContext(), ImageActivity.class);
                i.putExtra("url", getCommunity().getPhotoSrc().get(0));
                itemView.getContext().startActivity(i);
            }
            //super.onCardClick();

        }
    }

}
