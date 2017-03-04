package com.weaponzhi.merchandiselist.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weaponzhi.merchandiselist.base.baseMVP.BaseView;
import com.weaponzhi.merchandiselist.utils.MyApplication;
import com.weaponzhi.merchandiselist.utils.TUtil;

import butterknife.ButterKnife;

/**
 * BaseFragment
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:26 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements BaseView {
    protected View rootView;
    public P mPresenter;
    public M mModel;
    public BaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        attachPre();
        if (rootView == null)
            rootView = initViewPre(inflater, container);

        ButterKnife.bind(this, rootView);

        initView(rootView);
        loadData();
        return rootView;
    }

    @Override
    public void attachPre() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        activity = (BaseActivity) getActivity();
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
    }

    @Override
    public void loadData() {
        if (mPresenter != null) {
            mPresenter.loadData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApplication.getInstance().removeActivity(activity);
        ButterKnife.unbind(this);
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachVM(this, mModel);
        }
    }

    /**
     * 初始化界面
     *
     * @param from
     * @return 初始化后的view
     */
    protected abstract View initViewPre(LayoutInflater from, ViewGroup container);
}
