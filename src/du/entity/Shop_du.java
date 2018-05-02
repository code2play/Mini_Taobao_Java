package du.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商店类
 * Created by Shuhan Shen on 07／10／17.
 */
public class Shop_du {
    private String name; //店铺名称
    private int index;  //店铺在数据库ID
    private List<Integer> goods = new ArrayList<Integer>(); //店铺出售商品
    private String description; //店铺描述
    private double sales; //店铺销售量
    private double profit;//店铺利润
    private List<Order_du> orders = new ArrayList<Order_du>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Integer> getGoods() {
        return goods;
    }

    public void setGoods(List<Integer> goods) {
        this.goods = goods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void add_goods(int index){
    	goods.add(index);
    }
    
    public void remove_goods(int index){
    	goods.remove(Integer.valueOf(index));
    }

	public List<Order_du> getOrders() {
		return orders;
	}

	public void setOrders(List<Order_du> orders) {
		this.orders = orders;
	}
}
