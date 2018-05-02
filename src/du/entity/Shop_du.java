package du.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * �̵���
 * Created by Shuhan Shen on 07��10��17.
 */
public class Shop_du {
    private String name; //��������
    private int index;  //���������ݿ�ID
    private List<Integer> goods = new ArrayList<Integer>(); //���̳�����Ʒ
    private String description; //��������
    private double sales; //����������
    private double profit;//��������
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
