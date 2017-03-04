package com.weaponzhi.merchandiselist.presenter;

import com.weaponzhi.merchandiselist.base.BasePresenter;
import com.weaponzhi.merchandiselist.fragment.MerchandiseListBaseFragment;
import com.weaponzhi.merchandiselist.model.MerchandiseListBaseModel;

/**
 * MerchandiseListBasePresenter
 * <p>
 * author: 顾博君 <br>
 * time:   2016/11/8 13:08 <br>
 * e-mail: gubojun@csii.com.cn <br>
 * </p>
 */

public class MerchandiseListBasePresenter extends BasePresenter<MerchandiseListBaseFragment, MerchandiseListBaseModel> {
    //进页面时获取初始化的数据
    @Override
    public void loadData() {
        refresh();
    }

    /**
     * 刷新数据
     */
    public void refresh() {
        getData(mView.getType());
    }

    /**
     * 获取数据
     *
     * @param type
     */
    public void getData(int type) {
        mView.returnList(mModel.getList(type));
    }
    //上拉加载更多
    public void loadMore() {
        mModel.refreshList();
        getData(mView.getType());
    }

}
