package com.acemurder.datingme.modules.me;

import android.content.Context;

/**
 * Created by fg on 2016/8/25.
 */
public class PersonalPresenter implements PersonalContract.IPersonalPresenter{
    private PersonalContract.IPersonalView mIPersonalView;
    private Context mContext;

    public PersonalPresenter(Context context, PersonalContract.IPersonalView iPersonalView){
        mContext = context;
        mIPersonalView = iPersonalView;
    }


    @Override
    public void getPersonalDetails(String objectId) {
        //mIPersonalView.showPersonalDetails();
        //放数据进去
    }

    @Override
    public void bind(PersonalContract.IPersonalView view) {

    }

    @Override
    public void unBind() {
        this.mIPersonalView = null;
    }
}

