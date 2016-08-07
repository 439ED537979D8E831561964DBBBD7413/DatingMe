package com.acemurder.datingme;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public interface IBaseView<T>{
    void setPresenter(T presenter);
}
