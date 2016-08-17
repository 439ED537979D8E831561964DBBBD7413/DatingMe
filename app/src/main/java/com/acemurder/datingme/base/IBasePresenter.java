package com.acemurder.datingme.base;

//import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public interface IBasePresenter<T> {
    void bind(T view);
    void unBind();
}
