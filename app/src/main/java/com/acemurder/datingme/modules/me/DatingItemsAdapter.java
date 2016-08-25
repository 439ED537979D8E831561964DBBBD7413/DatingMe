package com.acemurder.datingme.modules.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.data.bean.DatingItem;

/**
 * Created by fg on 2016/8/25.
 */
public class DatingItemsAdapter extends RecyclerView.Adapter<DatingItemsAdapter.DatingItemsViewHolder> {
    private Context mContext;
    private DatingItem mDatingItemList;

    @Override
    public DatingItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DatingItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DatingItemsViewHolder extends RecyclerView.ViewHolder{

        public DatingItemsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
