package com.weaponzhi.merchandiselist.utils;

import android.app.Application;

import com.squareup.picasso.Picasso;
import com.weaponzhi.merchandiselist.BuildConfig;
import com.weaponzhi.merchandiselist.base.BaseActivity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * MyApplication
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:29 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MyApplication extends Application {

    //单例使用Application对象
    private static MyApplication instance;
    //list保存activity
    private List<BaseActivity> mList = new LinkedList<>();

    //构造方法
    public MyApplication() {
    }

    /**
     * 实例化Application，单例模式
     *
     * @return 返回单例的Application对象
     */
    public synchronized static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }

    //list中添加activity
    public void addActivity(BaseActivity activity) {
        mList.add(activity);
    }

    //判断mList中是否存在某个activity的实例
    public boolean containsActivity(Class<?> activity) {
        return mList.contains(activity);
    }

    public void finishAllActivityInList() {
        for (Iterator it = mList.iterator(); it.hasNext(); ) {
            BaseActivity activity = (BaseActivity) it.next();
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mList.clear();
    }

    public void removeActivity(BaseActivity activity) {
        mList.remove(activity);
    }

    //关闭list中的每个Activity
    public void exit() {
        try {
            for (BaseActivity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.DEBUG);
        //初始化Picasso图片加载框架
        final Picasso picasso = new Picasso.Builder(this)
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    //退出
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
