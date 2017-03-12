package com.weaponzhi.merchandiselist.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.weaponzhi.merchandiselist.R;
import com.weaponzhi.merchandiselist.base.BaseActivity;
import com.weaponzhi.merchandiselist.base.BaseFragmentAdapter;
import com.weaponzhi.merchandiselist.fragment.MerchandiseListBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * MerchandiseListActivity
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 18:03 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MerchandiseListActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    private List<String> mTitles = new ArrayList<String>() {
        {
            add("全部订单");
            add("待发货");
            add("待收货");
            add("已完成");
        }
    };

    @Override
    public View initViewPre(LayoutInflater from) {
        return from.inflate(R.layout.activity_merchandise_list, null);
    }

    @Override
    public void initView(View view) {
        initTitleBar(view, "我的订单");
        initSelect();
        initViewPager();
    }

    /**
     * 初始化水平选择栏
     */
    private void initSelect() {
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        //需要自定义布局时，需要在绑定viewpager前加入布局
        for (int i = 0; i < mTitles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitles.get(i)));
        }
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        MerchandiseListBaseFragment merchandiseListAll = MerchandiseListBaseFragment.
                newInstance(0);
        MerchandiseListBaseFragment merchandiseListDisPatch = MerchandiseListBaseFragment.
                newInstance(1);
        MerchandiseListBaseFragment merchandiseListReceive = MerchandiseListBaseFragment.
                newInstance(2);
        MerchandiseListBaseFragment merchandiseListFinish = MerchandiseListBaseFragment.
                newInstance(3);
        MerchandiseListBaseFragment.firstGetData();

        fragmentList.add(merchandiseListAll);
        fragmentList.add(merchandiseListDisPatch);//
        fragmentList.add(merchandiseListReceive);//
        fragmentList.add(merchandiseListFinish);//

        FragmentPagerAdapter fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                ((MerchandiseListBaseFragment) fragmentList.get(tab.getPosition())).fresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
