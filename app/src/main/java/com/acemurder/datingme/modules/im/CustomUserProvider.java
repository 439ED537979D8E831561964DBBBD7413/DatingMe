package com.acemurder.datingme.modules.im;

import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;


/**
 * Created by zhengyuxuan on 16/8/20.
 */

public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    private boolean hasLoaded = false;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为模拟数据，仅供参考
    static {
        RequestManager.INSTANCE.getAllLCChatKitUser(new SimpleSubscriber<List<LCChatKitUser>>(APP.getContext(), new SubscriberListener<List<LCChatKitUser>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<LCChatKitUser> lcChatKitUsers) {
                super.onNext(lcChatKitUsers);
                partUsers.addAll(lcChatKitUsers);
            }
        }));
        partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
        partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
        partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
        partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
        partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));

    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {

        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            Log.e("fetchProfiles", userId);
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    Log.e("fetchProfiles", "===============");
                    break;
                }
            }
        }

        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
       return partUsers;
    }
}


