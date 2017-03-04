package com.weaponzhi.merchandiselist.model;

import com.weaponzhi.merchandiselist.bean.MerchandiseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MerchandiseListBaseModel 数据Model
 * 因为每个view都会有一个presenter对象绑定，所以这里的Model设定为static域
 * 统一管理静态的订单数据
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/04 13:11 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MerchandiseListBaseModel extends SimpleModel {
    //每页的数据量
    private static int size = 5;
    private static int count = 0;
    //全部订单
    private static List<MerchandiseBean> listAll = new ArrayList<>();
    //待发货订单
    private static List<MerchandiseBean> listDisPatch = new ArrayList<>();
    //待收货订单
    private static List<MerchandiseBean> listReceive = new ArrayList<>();
    //已完成订单
    private static List<MerchandiseBean> listFinish = new ArrayList<>();


    /**
     * 根据标签类别获取订单List
     *
     * @return
     */
    public List<MerchandiseBean> getList(int type) {
        List<MerchandiseBean> list = new ArrayList<>();
        switch (type) {
            case 0:
                list = listAll;
                break;
            case 1:
                list = listDisPatch;
                break;
            case 2:
                list = listReceive;
                break;
            case 3:
                list = listFinish;
                break;
        }
        return list;
    }

    /**
     * 根据标签类别添加订单List元素
     */
    public static void addList(MerchandiseBean bean) {
        switch (Integer.parseInt(bean.getOrderState())) {
            case 1:
                listDisPatch.add(bean);
                break;
            case 2:
                listReceive.add(bean);
                break;
            case 3:
                listFinish.add(bean);
                break;
        }
        listAll.add(bean);
    }

    /**
     * 刷新时添加随机数据
     */
    public static void refreshList() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int orderNo = random.nextInt(3) + 1;
            String text = null;
            switch (orderNo) {
                case 1:
                    text = "待发货";
                    break;
                case 2:
                    text = "待收获";
                    break;
                case 3:
                    text = "已完成";
                    break;
            }
            MerchandiseBean bean = new MerchandiseBean();
            bean.setOrderState(String.valueOf(orderNo));
            bean.setOrderNo(String.valueOf(++count));
            bean.setGoodsName(text+"商品 "+count);
            bean.setBuyCount(random.nextInt(10)+1+"");
            bean.setPayTime("2017-03-01");
            bean.setTruePayAmt(random.nextInt(500)+"");
            addList(bean);
        }
    }
}
