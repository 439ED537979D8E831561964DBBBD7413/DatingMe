package com.acemurder.datingme.modules.im;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/12/4.
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSquareActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by zhengyuxuan on 15/12/4.
>>>>>>> ca15a169c1a9cf63ac170fca7f6072c7d424e97f
 * 联系人页面
 */
public class ContactFragment extends Fragment {

<<<<<<< HEAD
  protected SwipeRefreshLayout refreshLayout;
  protected RecyclerView recyclerView;

  private MembersAdapter itemAdapter;
  LinearLayoutManager layoutManager;
=======
  @BindView(R.id.chatting_tv_go_square)
  TextView mGoText;


  @OnClick(R.id.chatting_tv_go_square)
  public void OnClick(){
    Intent intent = new Intent(getActivity(), AVSquareActivity.class);
    intent.putExtra(Constants.CONVERSATION_ID, Constants.SQUARE_CONVERSATION_ID);
    intent.putExtra(Constants.ACTIVITY_TITLE, getString(R.string.square_name));
    startActivity(intent);

  }


>>>>>>> ca15a169c1a9cf63ac170fca7f6072c7d424e97f

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.contact_fragment, container, false);
<<<<<<< HEAD

    refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contact_fragment_srl_list);
    recyclerView = (RecyclerView) view.findViewById(R.id.contact_fragment_rv_list);

    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new LCIMDividerItemDecoration(getActivity()));
    itemAdapter = new MembersAdapter();
    recyclerView.setAdapter(itemAdapter);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refreshMembers();
      }
    });

    EventBus.getDefault().register(this);
=======
    ButterKnife.bind(this,view);
>>>>>>> ca15a169c1a9cf63ac170fca7f6072c7d424e97f
    return view;
  }

  @Override
  public void onDestroyView() {
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshMembers();
  }

  private void refreshMembers() {
<<<<<<< HEAD
    itemAdapter.setMemberList(CustomUserProvider.getInstance().getAllUsers());
    itemAdapter.notifyDataSetChanged();
    refreshLayout.setRefreshing(false);
  }

  /**
   * 处理 LetterView 发送过来的 MemberLetterEvent
   * 会通过 MembersAdapter 获取应该要跳转到的位置，然后跳转
   */
  public void onEvent(MemberLetterEvent event) {
    Character targetChar = Character.toLowerCase(event.letter);
    if (itemAdapter.getIndexMap().containsKey(targetChar)) {
      int index = itemAdapter.getIndexMap().get(targetChar);
      if (index > 0 && index < itemAdapter.getItemCount()) {
        layoutManager.scrollToPositionWithOffset(index, 0);
      }
    }
  }
=======

  }



>>>>>>> ca15a169c1a9cf63ac170fca7f6072c7d424e97f
}
