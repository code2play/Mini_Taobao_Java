package du.entity;

import du.biz.Mgr_du;

/**
 * 作为UI显示封装类
 * Created by ShuhanShen on 07／14／17.
 */
public class CartListForUI_du {
    private int goodIndex;
    private int num;
    private String name;
    private double price;
    private double totalPrice;

    public CartListForUI_du(){}
    public CartListForUI_du(int goodIndex, int num) {
        this.goodIndex = goodIndex;
        this.num = num;
        Goods_du tmp = Mgr_du.get_goods(goodIndex);
        this.name = tmp.getName();
        this.price = tmp.getPrice();
        this.totalPrice = this.price * num;
    }

    public int getGoodIndex() {
        return goodIndex;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
