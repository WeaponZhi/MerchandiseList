# MerchandiseList
类淘宝订单界面，用MVP框架简易搭建
这是效果图

![QQ图片20170305111231.png](http://upload-images.jianshu.io/upload_images/3363394-767e5cc8c36b8051.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![QQ图片20170305111245.png](http://upload-images.jianshu.io/upload_images/3363394-3a689c32f0e91104.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

值得一提的是，这里用到了一些其他开源代码，比如刷新样式swipetoloadlayout等，具体的使用方法大家可以百度搜下名字就行了，资料很多。

4个Fragment用的统一的静态构建方法，配合ViewPager和TabLayout进行一次性集成

```java
//统一的Fragment构建方法
    public static MerchandiseListBaseFragment newInstance(int flag) {
        Bundle args = new Bundle();
        //type代表页签，0：全部订单 1：待发货 2：待收货 3：已完成
        args.putString("type", String.valueOf(flag));
        MerchandiseListBaseFragment fragment = new MerchandiseListBaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
```

```java/**
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
        MerchandiseListBaseFragment.firstGetData();//初始化数据
		
		//持有fragment对象List
        fragmentList.add(merchandiseListAll);
        fragmentList.add(merchandiseListDisPatch);
        fragmentList.add(merchandiseListReceive);
        fragmentList.add(merchandiseListFinish);

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
        tabLayout.setupWithViewPager(viewPager);//将tabLayout和ViewPager绑定
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
```

订单列表是通过RecyclerView和swipetoloadlayout结合写的，具体代码参见源码（本文底部），因为这里只是提供一个淘宝订单页面的制作方法，所以这里的所有bean数据都暂时用的本地数据，没有添加网络框架进行数据加载，有兴趣的同学可以研究下MVP+RxJava+Okhttp+Retrofit，非常好用。

MVP构建通过在view层和presenter层中的`attachPre()`方法进行对象绑定：
```java
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
```

这里的getT方法是用来得到类的模板参数的类型的，返回表示此类型实际类型参数的 Type 实例化对象。这样就可以获取view<P,V>中P或者V的实际对象进行MVP绑定了。
```java
 public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
```
>以后有机会再研究下网络框架和MVP的集成

个人博客地址：[http://weaponzhi.online](http://weaponzhi.online)
