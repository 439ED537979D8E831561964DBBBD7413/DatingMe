package com.acemurder.datingme.data.network.function;

import android.util.Log;

import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.avos.avoscloud.okhttp.FormEncodingBuilder;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by zhengyuxuan on 16/8/27.
 */

public class RemarkFunc implements Func1<ResultWrapper<List<Remark>>,List<Remark>> {
    @Override
    public List<Remark> call(ResultWrapper<List<Remark>> listResultWrapper) {
        List<Remark> remarks = listResultWrapper.getResults();
        for (Remark remark : remarks){
            remark.setAuthorPhotoSrc(remark.getMaster().getPhotoSrc());
            Log.e("RemarkFunc",remark.getAuthorPhotoSrc());
        }
        return remarks;
    }
}
