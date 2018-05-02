package du.role;

import du.entity.Shop_du;
/**
 * 
 * @author Shibowen
 *
 */
public class Seller_du extends User_du {

	public Seller_du(String user_name, String password) {
		super(user_name, password);
		// TODO Auto-generated constructor stub
	}

	private static Shop_du shop = null;

	public static Shop_du getShop() {
		return shop;
	}

	public static void setShop(Shop_du shop) {
		Seller_du.shop = shop;
	}
}
