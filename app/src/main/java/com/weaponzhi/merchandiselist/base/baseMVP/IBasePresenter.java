package com.weaponzhi.merchandiselist.base.baseMVP;

/**
 * IBasePresenter
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:26 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public interface IBasePresenter<V, M> {
    /**
     * 绑定view,model
     *
     * @param view  视图
     * @param model 模型
     */
    void attachVM(V view, M model);

    /**
     * 解绑view
     */
//    void detachView();
}
