package du.biz;

import java.sql.*;

import du.entity.Cart_du;
import du.entity.Order_du;
import du.entity.Shop_du;
import du.role.Consumer_du;
import du.role.Seller_du;

/**
 * 
 * @author TJH
 *
 */
public class Login_du {
    private String url="jdbc:mysql://localhost:3306/taobao";    //JDBC的URL    
    private Connection conn;
    private static Statement stmt;
    private static boolean consumer=true;
    private static boolean seller=false;
    private Mgr_du mgr = new Mgr_du();  //建立链接
    
    public static boolean isConsumer() {
		return consumer;
	}

	public static void setConsumer(boolean consumer) {
		Login_du.consumer = consumer;
	}

	public static boolean isSeller() {
		return seller;
	}

	public static void setSeller(boolean seller) {
		Login_du.seller = seller;
	}

	public Login_du(){
        try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
            
            conn = DriverManager.getConnection(url,"root","tianjiahao1997");
            stmt = conn.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库！");
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void disconnect() throws SQLException{
		stmt.close();
		conn.close();
	}
	
    public int check(String un, String psw){
        int flag=0;
        try{
        	String sql;  //要执行的SQL
        	if(isSeller())
        		sql = "select * from sellers";    
        	else sql = "select * from consumers";
            ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while(rs.next()){
            	if(rs.getString(1).equals(un)){
            		flag++;
            		if(rs.getString(2).equals(psw)){
            			flag++;
            			break;
            		}
            	}
            }
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    
    public int new_account(String un, String psw){
        int flag=0;
        try{
        	String sql;  //要执行的SQL
            PreparedStatement pstmt;
        	if(isSeller()){
        		sql = "insert into sellers(user_name, psw, shop) values (?,?,?)";
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1, un);
                pstmt.setString(2, psw);
                pstmt.setInt(3, -1);
                flag = pstmt.executeUpdate();
        	}else{
        		sql = "insert into consumers(user_name, psw, name, address, money, cart_id, cart_num, orders)"
        				+ " values (?,?,?,?,?,?,?,?)";
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1, un);
                pstmt.setString(2, psw);
                pstmt.setString(3, un);
                pstmt.setString(4, "");
                pstmt.setDouble(5, 0.0);
                pstmt.setString(6, "");
                pstmt.setString(7, "");
                pstmt.setString(8, "");
                flag = pstmt.executeUpdate();
        	}
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
    }
    
	public static void load_seller(String un){
		try{
			String sql = "select * from sellers where user_name=\'"+un+"\'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
	            Seller_du.setUser_name(un);
	            Seller_du.setPassword(rs.getString(2));
	            int x = rs.getInt(3);
	            if(x>0)
	            	Seller_du.setShop(Mgr_du.get_shop(x));
	            else Seller_du.setShop(null);
            }
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void load_consumer(String un){
		Consumer_du.setAddress(null);
		Consumer_du.setMoney(0);
		Consumer_du.setName(null);
		Consumer_du.getOrders().clear();
		try{
			String sql = "select * from consumers where user_name=\'"+un+"\'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                Consumer_du.setUser_name(un);
                Consumer_du.setPassword(rs.getString(2));
                Consumer_du.setName(rs.getString(3));
                Consumer_du.setAddress(rs.getString(4));
                Consumer_du.setMoney(rs.getDouble(5));
                String id = rs.getString(6);
                String num = rs.getString(7);
                if(id.equals("")==false){
                    String[] ids = id.split(",");
                    String[] nums = num.split(",");
                    Cart_du cart = new Cart_du();
                    for(int i=0;i<ids.length;i++){
                    	int x = Integer.valueOf(ids[i]);
                    	int y = Integer.valueOf(nums[i]);
                    	cart.addGoods(x, y);
                    }
                    Consumer_du.setCart(cart);
                }
                String temp = rs.getString(8);
                if(temp.equals("")==false){
                    String[] c = temp.split(",");
                    Order_du o;
                    for(String str:c){
                    	o = Mgr_du.get_order(Integer.valueOf(str));
                    	Consumer_du.getOrders().add(o);
                    }
                }
            }
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
}
