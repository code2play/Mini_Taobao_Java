package du.entity;

import du.helping.CartNode_du;

import java.util.ArrayList;
import java.util.List;

/**
 * ∂®µ•¿‡
 * Created by ShuhanShen on 07£Ø10£Ø17.
 */
public class Order_du {
    private List<CartNode_du> orderList = new ArrayList();
    private String date;
    private double totalPrice;
    private int index;
    private String buyer;
    private String address;

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<CartNode_du> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CartNode_du> orderList) {
        this.orderList = orderList;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
