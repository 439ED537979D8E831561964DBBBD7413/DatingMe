package com.acemurder.datingme.modules.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.data.bean.Remark;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/23.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private Context mContext;
    private List<Remark>mRemarkList;

    public DetailsAdapter(Context context,List<Remark>remarkList){
            mContext = context;
            mRemarkList = remarkList;
    }
    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_details,parent,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        if (mRemarkList != null){
            try {
                Glide.with(mContext).load(mRemarkList.get(position).getAuthorPhotoSrc())
                        .centerCrop().placeholder(R.drawable.ic_proxy).crossFade().into(holder.portraitView);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }

            holder.nameView.setText(mRemarkList.get(position).getAuthorName());
            holder.dateView.setText(mRemarkList.get(position).getUpdatedAt());
            holder.contentView.setText(mRemarkList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mRemarkList.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.details_custom_portrait)CircleImageView portraitView;
        @BindView(R.id.item_details_tv_name)TextView nameView;
        @BindView(R.id.item_details_tv_date)TextView dateView;
        @BindView(R.id.item_details_tv_content)TextView contentView;
        public DetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
