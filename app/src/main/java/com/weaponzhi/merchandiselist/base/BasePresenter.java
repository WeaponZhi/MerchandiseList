package com.weaponzhi.merchandiselist.base;

import android.content.Context;

import com.weaponzhi.merchandiselist.base.baseMVP.BaseView;
import com.weaponzhi.merchandiselist.base.baseMVP.IBasePresenter;

/**
 * BasePresenter
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:25 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> implements IBasePresenter<V, M> {
    public Context mContext;
    public M mModel;
    public V mView;

    /**
     * MVP绑定
     * @param view  视图
     * @param model 模型
     */
    @Override
    public void attachVM(V view, M model) {
        mView = view;
        mModel = model;
    }

    /**
     * 加载数据<br>
     */
    public abstract void loadData();

    /**
     * 防止内存泄漏，回收资源
     * 或者在引用View对象的时候使用更容易回收的软引用，弱引用
     */
    public void onDestroy(){
        mView = null;
        if(mModel != null) {
            mModel.cancleTasks();
        }
    }
}
