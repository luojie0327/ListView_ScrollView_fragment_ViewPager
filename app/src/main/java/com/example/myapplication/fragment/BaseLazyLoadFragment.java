package com.example.myapplication.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by mumu on 2019/3/29.
 */

public abstract class BaseLazyLoadFragment extends Fragment {

    //是否是第一次加载数据
    protected boolean mIsFirstGetData = true;
    //fragment对用户是否可见
    protected boolean mIsVisibleToUser;
    //根布局
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //只有为空的时候加载布局，
        // 因为在viewpager中fragment对象不会被销毁，成员变量也不会销毁
        if (mRootView == null) {
            mRootView = inflater.inflate(provideLayoutId(), container, false);
            initView();
            Log.d("Fragment0========","onCreateView:初始化view");
        }
        Log.d("Fragment0========","onCreateView:view有了，不需要初始化");
        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //只要加载过数据，下次再回到该fragment中，肯定会走setUserVisibleHint（true）
        //所以onActivityCreated得地方只要之前加载过数据，就没有机会再次加载
        //否则会重复加载     ps：fragment3直接跳转回fragment1
        if (!mIsFirstGetData) {
            Log.d("Fragment0========","onActivityCreated:不是首次加载，不需要加载数据");
            return;
        }
        //懒加载数据
        lazyLoadData();
        Log.d("Fragment0========","onActivityCreated:开始懒加载");
    }

    //fragment是否需要实时刷新，如果需要返回true，不需要返回false
    protected abstract boolean setIsRealTimeRefresh();


    protected abstract void initData();

    protected abstract void initView();

    protected abstract int provideLayoutId();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //设置可视状态
        mIsVisibleToUser = isVisibleToUser;

        //懒加载数据
        lazyLoadData();
    }

    public void lazyLoadData() {
        //如果不是每次刷新数据，且如果已经加载过数据，那么不执行initData
        if (!setIsRealTimeRefresh() && !mIsFirstGetData) {
            Log.d("Fragment0========","lazyLoadData:不是首次加载或者没要求刷新数据");
            return;
        }
        //可见并且rootView不为空的时候才能加载数据
        if (mIsVisibleToUser && mRootView != null) {
            mIsFirstGetData = false;
            Log.e("TAG", "initData");
            Log.d("Fragment0========","lazyLoadData:开始加载数据");
            initData();
        }
    }

}
