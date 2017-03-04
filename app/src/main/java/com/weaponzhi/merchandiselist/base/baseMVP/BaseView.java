package com.weaponzhi.merchandiselist.base.baseMVP;

import android.view.View;

/**
 * BaseView
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:26 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public interface BaseView {
    /**
     * 加载数据<br>
     * 本地或网络加载框架放在这里<br>
     * 在此方法中调用 {@code mPresenter.loadData();}
     */
    void loadData();
    /**
     * 绑定数据前需要完成的工作
     * <p>
     * 例如：Presenter绑定view<br>
     * {@code mPresenter = new XXXPresenter(this);}<br>
     * {@code mPresenter.attachVM(this,mModel);}
     * </p>
     */
    void attachPre();

    void initView(View view);

    /**
     * 初始化界面
     * 只负责new一个新类和绑定layout
     *
     * @param from
     * @return
     */
}
