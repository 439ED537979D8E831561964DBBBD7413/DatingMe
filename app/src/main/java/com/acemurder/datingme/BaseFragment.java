package com.acemurder.datingme;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.base.BaseActivity;

/**
 * Created by fg on 2016/8/15.
 */

public abstract class BaseFragment extends Fragment {

        //避免getActivity报空指针异常
        protected BaseActivity mActivity;

        //获取布局文件ID
        protected abstract int getLayoutId();

        //获取宿主Activity
        protected BaseActivity getHoldingActivity() {
            return mActivity;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.mActivity = (BaseActivity) context;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(getLayoutId(), container, false);
            initView(view);
            return view;
        }

        /**
         * 此方法可以得到上下文对象
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        /**
         * 子类可以复写此方法初始化事件
         */
        protected void initEvent() {

        }

        /**
         * 当Activity初始化之后可以在这里进行一些数据的初始化操作
         */
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            initData();
            initEvent();
        }

        /**
         * view操作
         */
        protected abstract void initView(View view);

        /**
         * 子类在此方法中实现数据的初始化
         */
        public abstract void initData();
    }


