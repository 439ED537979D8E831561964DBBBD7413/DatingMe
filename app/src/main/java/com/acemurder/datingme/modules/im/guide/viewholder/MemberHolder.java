package com.acemurder.datingme.modules.im.guide.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.acemurder.datingme.R;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.acemurder.datingme.modules.im.guide.adapter.MembersAdapter;

import butterknife.BindView;

/**
 * Created by wli on 15/8/14.
 */
public class MemberHolder extends AVCommonViewHolder {

  @BindView(R.id.member_item_name)
  public TextView mTextView;

  public MemberHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.activity_member_item);
  }

  @Override
  public void bindData(Object o) {
    final MembersAdapter.MemberItem item = (MembersAdapter.MemberItem) o;
    mTextView.setText(item.content);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Activity host = (Activity) itemView.getContext();
        Intent intent = new Intent(host, AVSingleChatActivity.class);
        intent.putExtra(Constants.MEMBER_ID, item.content);
        host.startActivity(intent);
      }
    });
  }
}