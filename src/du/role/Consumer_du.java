package du.role;

import java.util.ArrayList;
import java.util.List;

import du.entity.Cart_du;
import du.entity.Order_du;
/**
 * 
 * @author Shibowen
 *
 */
public class Consumer_du extends User_du {
	public Consumer_du(String user_name, String password) {
		super(user_name, password);
		// TODO Auto-generated constructor stub
	}
	
	private static String name;
	private static String address;
	private static Cart_du cart = new Cart_du();
	private static List<Order_du> orders = new ArrayList<Order_du>();
	private static double money;
	
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Consumer_du.name = name;
	}
	public static String getAddress() {
		return address;
	}
	public static void setAddress(String address) {
		Consumer_du.address = address;
	}
	public static Cart_du getCart() {
		return cart;
	}
	public static void setCart(Cart_du cart) {
		Consumer_du.cart = cart;
	}
	public static List<Order_du> getOrders() {
		return orders;
	}
	public static void setOrders(List<Order_du> orders) {
		Consumer_du.orders = orders;
	}
	public static double getMoney() {
		return money;
	}
	public static void setMoney(double money) {
		Consumer_du.money = money;
	}
	public static void recharge(double m){
		money+=m;
	}
	public static void consume(double m){
		money-=m;
	}
	public static void add_order(Order_du o){
		orders.add(0, o);
	}
}
