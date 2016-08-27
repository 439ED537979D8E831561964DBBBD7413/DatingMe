package com.acemurder.datingme.data.network.function;

import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.ResultWrapper;

import java.util.List;
import java.util.concurrent.Callable;

import rx.functions.Func1;

/**
 * Created by zhengyuxuan on 16/8/27.
 */

public class CommunityFunc implements Func1<ResultWrapper<List<Community>>,List<Community>> {
    @Override
    public List<Community> call(ResultWrapper<List<Community>> listResultWrapper) {
        List<Community>communities = listResultWrapper.getResults();
        for (Community c : communities){
            c.setAuthorPhoto(c.getMaster().getPhotoSrc());
        }
        return communities;
    }
}
