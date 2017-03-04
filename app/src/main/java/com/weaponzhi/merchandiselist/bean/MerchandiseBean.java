package com.weaponzhi.merchandiselist.bean;

/**
 * MerchandiseBean
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/02 22:25 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class MerchandiseBean {

    private String OrderState;//订单的页面状态
    private String OrderNo;//订单序号
    private String PayTime;//购买事件
    private String BuyCount;//购买数量
    private String GoodsName;//商品名称
    private String TruePayAmt;//购买总价

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String OrderState) {
        this.OrderState = OrderState;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public String getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(String BuyCount) {
        this.BuyCount = BuyCount;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String GoodsName) {
        this.GoodsName = GoodsName;
    }


    public String getTruePayAmt() {
        return TruePayAmt;
    }

    public void setTruePayAmt(String truePayAmt) {
        TruePayAmt = truePayAmt;
    }
}
