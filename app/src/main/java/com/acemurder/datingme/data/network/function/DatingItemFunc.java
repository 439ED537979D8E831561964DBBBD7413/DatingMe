package com.acemurder.datingme.data.network.function;

import android.util.Log;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.data.bean.User;

import java.util.List;

import rx.functions.Func1;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengyuxuan on 16/8/26.
 */

public class DatingItemFunc implements Func1<ResultWrapper<List<DatingItem>>,List<DatingItem>> {
    @Override
    public List<DatingItem> call(ResultWrapper<List<DatingItem>> listResultWrapper) {
        List<DatingItem> datingItems = listResultWrapper.getResults();
        for (DatingItem datingItem : datingItems){
            User master = datingItem.getMaster();
            User recipietn = datingItem.getRecipietn();
            if (master.getPhotoSrc() != null){
                datingItem.setPromulgatorPhoto(master.getPhotoSrc());
                Log.e("DatingItemFunc",datingItem.getPromulgatorPhoto());

            }
            if (recipietn.getPhotoSrc() != null){
                datingItem.setReceiverPhoto(recipietn.getPhotoSrc());
            }
        }
        return datingItems;
    }
}
