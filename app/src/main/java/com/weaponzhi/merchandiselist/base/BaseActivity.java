package com.weaponzhi.merchandiselist.base;

/**
 * BaseActivity
 * /**
 * ************************************************************************
 * **                              _oo0oo_                               **
 * **                             o8888888o                              **
 * **                             88" . "88                              **
 * **                             (| -_- |)                              **
 * **                             0\  =  /0                              **
 * **                           ___/'---'\___                            **
 * **                        .' \\\|     |// '.                          **
 * **       张冠之          / \\\|||  :  |||// \\         顾博君           **
 * **                      / _ ||||| -:- |||||- \\                       **
 * **                      | |  \\\\  -  /// |   |                       **
 * **                      | \_|  ''\---/''  |_/ |                       **
 * **                      \  .-\__  '-'  __/-.  /                       **
 * **                    ___'. .'  /--.--\  '. .'___                     **
 * **                 ."" '<  '.___\_<|>_/___.' >'  "".                  **
 * **                | | : '-  \'.;'\ _ /';.'/ - ' : | |                 **
 * **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 **
 * **            ====='-.____'.___ \_____/___.-'____.-'=====             **
 * **                              '=---='                               **
 * ************************************************************************
 * **                        佛祖保佑      镇类之宝                        **
 * ************************************************************************
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weaponzhi.merchandiselist.R;
import com.weaponzhi.merchandiselist.base.baseMVP.BaseView;
import com.weaponzhi.merchandiselist.utils.MyApplication;
import com.weaponzhi.merchandiselist.utils.TUtil;

import butterknife.ButterKnife;

/**
 * BaseActivity<br>
 * 本程序使用MVP模式开发 目标是实现界面和数据完全分离；
 * 界面和数据交互和业务逻辑在Presenter中完成；
 * 使用的Butterknife已经在BaseActivity中的onCreate绑定；
 * BaseActivity的子类无需再次绑定和解绑；
 * <br>
 * <br>
 * 简单使用方法：
 * 在BaseActivity子类中需要使用逻辑处理调用<code>mPresenter.XXX()</code>
 * <br>
 * 在{@link BasePresenter}子类中需要使用产生界面变动 调用<codw>mView.XXX()</codw>
 * 注意：BasePresenter的实现类不能用带有参数的构造方法
 * <br>
 * 在{@link BaseModel}子类中可以实现网络请求和返回的封装，在baseModel中已经实现了部分请求和返回的封装
 * 这里只是象征性的在Model层塞了些本地数据，后期有时间可以集成Rxjava+OkHttp+Retrofit2
 * 注意：BaseModel的实现类不能用带有参数的构造方法
 * <br>
 * MVP框架参考的是著名MVP项目AndroidFire，有兴趣的朋友可以去Github看下
 * 所有的数据目前都用的本地随机数据，以后有时间集成下网络框架
 * 同时感谢同事顾老师给我的帮助，他为该框架的搭建提供了非常多的指导和帮助(gubojun@csii.com.cn)
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 15:25 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements BaseView {
    private static final int ID_PRESENTER = 0;
    private static final int ID_MODEL = 1;

    public P mPresenter;
    public M mModel;
    public MyApplication myApplication;
    public BaseActivity activity;
    public View view;

    public LinearLayout headBack;
    public TextView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定前的操作，新建Presenter和model
        attachPre();
        //获取视图
        view = initViewPre(LayoutInflater.from(this));
        if (view != null)
            setContentView(view);
        //ButterKnife绑定
        ButterKnife.bind(this);
        myApplication = (MyApplication) getApplicationContext();
        //视图更新操作
        initView(view);
        //加载数据
        loadData();
    }

    /**
     * MVP绑定
     */
    @Override
    public void attachPre() {
        activity = this;
        // mContext = this;
        mPresenter = TUtil.getT(this, ID_PRESENTER);
        mModel = TUtil.getT(this, ID_MODEL);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        initPresenter();
    }

    /**
     * BaseView方法，数据初始化操作
     */
    @Override
    public void loadData() {
        if (mPresenter != null) {
            mPresenter.loadData();
        }
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public void initPresenter() {
        if (mPresenter != null) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        MyApplication.getInstance().removeActivity(activity);
        //防止内存泄漏，需要Presenter和Model执行资源释放操作
        mPresenter.onDestroy();
    }

    /**
     * 展示标题头
     * 是否展示back
     *
     * @param view 布局
     */
    public void initTitleBar(View view, String title) {
        titleView = (TextView) view.findViewById(R.id.head_title);
        titleView.setText(title);
    }

   public abstract View initViewPre(LayoutInflater from);
}
