package com.weaponzhi.merchandiselist.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.weaponzhi.merchandiselist.R;
import com.weaponzhi.merchandiselist.adapter.MerchandiseListAdapter;
import com.weaponzhi.merchandiselist.base.BaseFragment;
import com.weaponzhi.merchandiselist.bean.MerchandiseBean;
import com.weaponzhi.merchandiselist.model.MerchandiseListBaseModel;
import com.weaponzhi.merchandiselist.presenter.MerchandiseListBasePresenter;
import com.weaponzhi.merchandiselist.utils.ToastUtil;
import com.weaponzhi.merchandiselist.view.recyclerview.MyRecyclerView;
import com.weaponzhi.merchandiselist.view.recyclerview.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;

/**
 * MerchandiseListBaseFragment
 * <p>
 * author: 顾博君 <br>
 * time:   2016/11/8 10:56 <br>
 * e-mail: gubojun@csii.com.cn <br>
 * </p>
 */

public class MerchandiseListBaseFragment extends BaseFragment<MerchandiseListBasePresenter, MerchandiseListBaseModel> implements OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;//刷新布局，详细用法请参考网上资料
    @Bind(R.id.swipe_target)
    MyRecyclerView swipeTarget;//
    MerchandiseListAdapter adapter;
    public int type;
    private boolean isPrepared = false;

    @Override
    protected View initViewPre(LayoutInflater from, ViewGroup container) {
        return from.inflate(R.layout.fragment_merchandise_list, container, false);
    }

    @Override
    public void initView(View view) {
        /*
         * 获取页签类型  0全部 1待发货 2待收货 3已完成
         */
        Bundle args = getArguments();
        if (args != null) {
            String typeTemp = args.getString("type");
            type = Integer.valueOf(typeTemp);
        }
        adapter = new MerchandiseListAdapter(activity);
        adapter.setOnItemClickListener(new MerchandiseListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int position, MerchandiseBean data) {
                //设置点击订单时候的跳转界面
                ToastUtil.showToast(activity, "跳转到订单" + data.getOrderNo() + "界面");
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, View v, int position) {
            }
        });
        adapter.setButtonClickListener(new MerchandiseListAdapter.ButtonClickListener() {
            @Override
            public void onClick(int position, final MerchandiseBean data) {
                //设置每个bean上面的按钮监听
                String text = null;
                switch (data.getOrderState()) {
                    case "1":
                        text = "退货";
                        break;
                    case "2":
                        text = "确认收货";
                        break;
                    case "3":
                        text = "删除订单";
                        break;
                }
                ToastUtil.showToast(activity, "按钮" + text + "被点击了！");
            }
        });
        swipeTarget.setAdapter(adapter);
        swipeTarget.setHasFixedSize(true);
        swipeTarget.setEmptyView(view.findViewById(R.id.layout_empty));
        swipeTarget.setLayoutManager(new LinearLayoutManager(activity));
        swipeTarget.addItemDecoration(new SpaceItemDecoration(activity, 5));
        //----------------------------------------------------------
        //刷新
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

//        setLoadMoreEnabled(false);
        swipeToLoadLayout.setDefaultToRefreshingScrollingDuration(1000);//默认下拉刷新滚动时间
        swipeToLoadLayout.setReleaseToRefreshingScrollingDuration(1000);//释放下拉刷新持续滚动的时间
//        swipeToLoadLayout.setRefreshCompleteToDefaultScrollingDuration(1000);//默认完成下拉刷新持续滚动时间
//        swipeToLoadLayout.setRefreshCompleteDelayDuration(1000);//下拉刷新完成延迟的持续时间
        swipeToLoadLayout.setDefaultToLoadingMoreScrollingDuration(1000);
        swipeToLoadLayout.setReleaseToLoadingMoreScrollingDuration(1000);

        swipeTarget.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        isPrepared = true;
    }

    //统一的Fragment构建方法
    public static MerchandiseListBaseFragment newInstance(int flag) {
        Bundle args = new Bundle();
        //type代表页签，0：全部订单 1：待发货 2：待收货 3：已完成
        args.putString("type", String.valueOf(flag));
        MerchandiseListBaseFragment fragment = new MerchandiseListBaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取订单类型
     *
     * @return
     */
    public int getType() {
        return type;
    }

    //处理订单列表数据
    public void returnList(List<MerchandiseBean> list) {
        adapter.flush(list);
    }

    public void fresh() {
        mPresenter.refresh();
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.refresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeToLoadLayout != null)
                    swipeToLoadLayout.setRefreshing(false);
            }
        }, 2000);
    }
    //上拉加载更多
    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeToLoadLayout != null)
                    swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);
    }
    //初始化数据
    public static void firstGetData() {
        MerchandiseListBaseModel.refreshList();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepared) {
            //相当于Fragment的onResume方法
            fresh();
        } else {
            //相当于Fragment的onPause
        }
    }
}
