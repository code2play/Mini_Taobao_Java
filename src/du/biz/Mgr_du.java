package du.biz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import du.entity.Goods_du;
import du.entity.Order_du;
import du.entity.Shop_du;
import du.helping.CartNode_du;
import du.helping.Comments_du;
import du.role.Consumer_du;
import du.role.Seller_du;

/**
 * 
 * @author TJH
 *
 */
public class Mgr_du {
    private String url="jdbc:mysql://localhost:3306/taobao";    //JDBC的URL    
    private static Connection conn;
    public static List<Goods_du> goods_res = new ArrayList<Goods_du>(); //搜索商品的结果
    public static List<Goods_du> goods_from_shop = new ArrayList<Goods_du>(); //从商店搜索物品的结果
    public static List<Goods_du> hot_sales = new ArrayList<Goods_du>();  //热销
    public static List<Shop_du> shops_res = new ArrayList<Shop_du>(); //搜索商店的结果
	public Mgr_du(){
		try{
	        //调用Class.forName()方法加载驱动程序
	        Class.forName("com.mysql.jdbc.Driver");
	        System.out.println("成功加载MySQL驱动！");
	        
	        conn = DriverManager.getConnection(url,"root","tianjiahao1997");
	         //创建Statement对象
	        System.out.println("成功连接到数据库！");
		}
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}
	
	public void disconnect() throws SQLException{
		conn.close();
	}

	/**
	 * 添加商品
	 * @param name 商品名称
	 * @param price 商品价格
	 * @param shop 商品所属商店
	 * @param stock 商品库存
	 * @param description 商品描述
	 * @return 商品的id
	 */
	public static int add_goods(String name, double price, int shop, int stock, String description){
		int flag = -1;
		try{
			PreparedStatement pstmt;
			String sql = "insert into goods(name,price,stock,shop,description,sales,rate,rate_num,comments)"
					+ " values (?,?,?,?,?,?,?,?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, stock);
            pstmt.setInt(4, shop);
            pstmt.setString(5, description);
            pstmt.setInt(6, 0);
            pstmt.setDouble(7, 5.0);
            pstmt.setInt(8, 0);
            pstmt.setString(9, "");
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            	flag = rs.getInt(1);
            pstmt.close();
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	}
	
	public static void remove_goods(int id){
		try{
			String sql = "delete from goods where id=" + id;
			Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static Comments_du get_comment(int id){
		Comments_du c = null;
		try{
			String sql = "select * from comments where id=" + id;
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
            	c = new Comments_du();
            	c.setIndex(rs.getInt(1));
            	c.setTimeAndDate(rs.getString(2));
            	c.setAuthor(rs.getString(3));
            	c.setComment(rs.getString(4));
            }
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return c;
	}
	
	public static Order_du get_order(int id){
		Order_du o = null;
		try{
			String sql = "select * from orders where id=" + id;
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
            	o = new Order_du();
            	o.setIndex(rs.getInt(1));
            	o.setDate(rs.getString(2));
            	String i = rs.getString(3);
            	String n = rs.getString(4);
            	String[] ids = i.split(",");
            	String[] num = n.split(",");
            	for(int j=0;j<ids.length;j++){
            		CartNode_du c = new CartNode_du();
            		c.setGoodIndex(Integer.valueOf(ids[j]));
            		c.setNum(Integer.valueOf(num[j]));
            		o.getOrderList().add(c);
            	}
            	o.setTotalPrice(rs.getDouble(5));
            }
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return o;
	}
	
	public static Order_du get_order_for_seller(int id){
		Order_du o = null;
		try{
			String sql = "select * from shop_orders where id=" + id;
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
            	o = new Order_du();
            	o.setIndex(rs.getInt(1));
            	o.setBuyer(rs.getString(2));
            	o.setAddress(rs.getString(3));
            	String i = rs.getString(4);
            	String n = rs.getString(5);
            	String[] ids = i.split(",");
            	String[] num = n.split(",");
            	for(int j=0;j<ids.length;j++){
            		CartNode_du c = new CartNode_du();
            		c.setGoodIndex(Integer.valueOf(ids[j]));
            		c.setNum(Integer.valueOf(num[j]));
            		o.getOrderList().add(c);
            	}
            	o.setDate(rs.getString(6));
            	o.setTotalPrice(rs.getDouble(7));
            }
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return o;
	}
	
	/**
	 * 搜索商品
	 * @param key_words 关键字
	 * @return 搜索到的商品数目，搜索结果存放于Mgr_du.goods_res中
	 */
	public int search_goods(String key_words){
		Goods_du g = null;
		goods_res.clear();
		try{
			String sql = "select * from goods";
			Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while(rs.next()){
            	if(rs.getString(2).contains(key_words) || rs.getString(6).contains(key_words)){
                    g=new Goods_du();
                    g.setIndex(rs.getInt(1));
                    g.setName(rs.getString(2));
                    g.setPrice(rs.getDouble(3));
                    g.setStock(rs.getInt(4));
                    g.setShop(rs.getInt(5));
                    g.setDescription(rs.getString(6));
                    g.setSales(rs.getInt(7));
                    g.setRate(rs.getDouble(8));
                    g.setRateNum(rs.getInt(9));
                    String temp = rs.getString(10);
                    if(temp.equals("")==false){
                        String[] c = temp.split(",");
                        for(String x : c){
                        	int index = Integer.valueOf(x);
                        	Comments_du cc = Mgr_du.get_comment(index);
                        	g.addComment(cc);
                        }
                    }
                    goods_res.add(g);
            	}
            }
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return goods_res.size();
	}
	/**
	 * 搜索店铺
	 * @param key_words 关键字
	 * @return 搜索到的店铺数目，搜索结果存放于Mgr_du.shops_res中
	 */
	public int search_shops(String key_words){
		Shop_du s = null;
		shops_res.clear();
		try{
			String sql = "select * from shops";
			Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);//创建数据对象
            while(rs.next()){
            	if(rs.getString(2).contains(key_words) || rs.getString(3).contains(key_words)){
            		s = new Shop_du();
            		s.setIndex(rs.getInt(1));
            		s.setName(rs.getString(2));
            		s.setDescription(rs.getString(3));
            		s.setSales(rs.getDouble(4));
                    String temp = rs.getString(5);
                    String[] goods = temp.split(",");
                    for(String str:goods){
                    	if(str.equals("")==false)
                    		s.add_goods(Integer.valueOf(str));
                    }
                    temp = rs.getString(6);
                    String[] orders = temp.split(",");
                    Order_du o;
                    for(String str:orders){
                    	if(str.equals("")==false){
                    		o = get_order_for_seller(Integer.valueOf(str));
                    		s.getOrders().add(o);
                    	}
                    }
                    shops_res.add(s);
            	}
            }
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return shops_res.size();
	}
	/**
	 * 通过id获取商品
	 * @param id 商品编号
	 * @return 商品
	 */
	public static Goods_du get_goods(int id){
		Goods_du g = null;
		try{
			String sql = "select * from goods where id=" + id;
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            if(rs.next()){
	            g=new Goods_du();
	            g.setIndex(rs.getInt(1));
	            g.setName(rs.getString(2));
	            g.setPrice(rs.getDouble(3));
	            g.setStock(rs.getInt(4));
	            g.setShop(rs.getInt(5));
	            g.setDescription(rs.getString(6));
	            g.setSales(rs.getInt(7));
	            g.setRate(rs.getDouble(8));
	            g.setRateNum(rs.getInt(9));
                String temp = rs.getString(10);
                if(temp.equals("")==false){
                    String[] c = temp.split(",");
                    for(String x : c){
                    	int index = Integer.valueOf(x);
                    	Comments_du cc = get_comment(index);
                    	g.addComment(cc);
                    }
                }
            }
            rs.close();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return g;
	}
	/**
	 * 获取某商店的所有物品
	 * @param id 商店编号
	 * @return 获取的商品个数，商品存放于Mgr_du.goods_from_shop中
	 */
	public int get_goods_from_shop(int id){
		goods_from_shop.clear();
		Goods_du g = null;
		try{
			String sql = "select * from goods where shop=\'"+id+"\'";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery(sql);
	        while(rs.next()){
	        	g = new Goods_du();
	            g.setIndex(rs.getInt(1));
	            g.setName(rs.getString(2));
	            g.setPrice(rs.getDouble(3));
	            g.setStock(rs.getInt(4));
	            g.setShop(rs.getInt(5));
	            g.setDescription(rs.getString(6));
	            g.setSales(rs.getInt(7));
	            g.setRate(rs.getDouble(8));
	            g.setRateNum(rs.getInt(9));
                String temp = rs.getString(10);
                if(temp.equals("")==false){
                    String[] c = temp.split(",");
                    for(String x : c){
                    	int index = Integer.valueOf(x);
                    	Comments_du cc = get_comment(index);
                    	g.addComment(cc);
                    }
                }
	            goods_from_shop.add(g);
			}
            rs.close();
	        pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return goods_from_shop.size();
	}
	
	/**
	 * 从数据库中取出商店
	 * @param id 商店编号
	 * @return Shop
	 */
	public static Shop_du get_shop(int id){
		Shop_du s = null;
		try{
			String sql = "select * from shops where id=" + id;
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            s = new Shop_du();
            if(rs.next()){
                s.setIndex(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDescription(rs.getString(3));
                s.setSales(rs.getDouble(4));
                String temp = rs.getString(5);
                String[] goods = temp.split(",");
                for(String str:goods){
                	if(str.equals("")==false)
                		s.add_goods(Integer.valueOf(str));
                }
                temp = rs.getString(6);
                String[] orders = temp.split(",");
                Order_du o;
                for(String str:orders){
                	if(str.equals("")==false){
                		o = get_order_for_seller(Integer.valueOf(str));
                		s.getOrders().add(o);
                	}
                }
            }
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return s;
	}
	
	/**
	 * 新建一个商店
	 * @param name 商店名
	 * @param description 商店描述
	 * @return 新建商店的id
	 */
	public static int add_shop(String name, String description){
		int flag = -1;
		try{
			String sql = "insert into shops(name,description,sales,goods,orders) values (?,?,?,?,?)";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, 0);
            pstmt.setString(4, "");
            pstmt.setString(5, "");
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            	flag = rs.getInt(1);
            rs.close();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	}
	
	public int remove_shop(int id){
		int flag = 0;
		try{
			String sql = "delete from shops where id=" + id;
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            flag = pstmt.executeUpdate();
            pstmt.close();
            
            //TODO 把商店的所有商品也删除？
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	} 
	
	public static void save_goods(Goods_du g){
		try{
			String sql = "update goods set name=?, price=?, stock=?, shop=?, description=?, sales=?, "
						+ "rate=?, rate_num=?, comments=? where id=" + g.getIndex();
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, g.getName());
            pstmt.setDouble(2, g.getPrice());
            pstmt.setInt(3, g.getStock());
            pstmt.setInt(4, g.getShop());
            pstmt.setString(5, g.getDescription());
            pstmt.setInt(6, g.getSales());
            pstmt.setDouble(7, g.getRate());
            pstmt.setDouble(8, g.getRateNum());
            String temp = new String();
            for(Comments_du c : g.getComments()){
            	temp += c.getIndex();
            	temp += ",";
            }
            pstmt.setString(9, temp);
            pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void save_shop(){
		Shop_du s = Seller_du.getShop();
		try{
			String sql = "update shops set name=?, description=?, sales=?, goods=?, orders=?"
					+ " where id=" + s.getIndex();
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, s.getName());
            pstmt.setString(2, s.getDescription());
            pstmt.setDouble(3, s.getSales());
            String temp = new String();
            for(int i:s.getGoods())
            	temp += String.valueOf(i) + ",";
            pstmt.setString(4, temp);
            temp="";
            for(Order_du o : s.getOrders())
            	temp += String.valueOf(o.getIndex()) + ",";
            pstmt.setString(5, temp);
            pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void save_consumer(){
		try{
			String sql = "update consumers set psw=?, name=?, address=?, money=?, cart_id=?, cart_num=?, orders=?"
					+ " where user_name=\'"+ Consumer_du.getUser_name() + "\'";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, Consumer_du.getPassword());
            pstmt.setString(2, Consumer_du.getName());
            pstmt.setString(3, Consumer_du.getAddress());
            pstmt.setDouble(4, Consumer_du.getMoney());
            String id = new String();
            String num = new String();
            for(CartNode_du i:Consumer_du.getCart().getCartList()){
            	id += i.getGoodIndex();
            	id += ",";
            	num += i.getNum();
            	num += ",";
            }
            pstmt.setString(5, id);
            pstmt.setString(6, num);
            String o = new String();
            for(Order_du order : Consumer_du.getOrders()){
            	 o += order.getIndex();
            	 o += ",";
            }
            pstmt.setString(7, o);
            pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}

	public static void save_seller(){
		try{
			String sql = "update sellers set psw=?, shop=? where user_name=\'"
						+ Seller_du.getUser_name() + "\'";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, Seller_du.getPassword());
            pstmt.setInt(2, Seller_du.getShop().getIndex());
            pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public int get_hot_sales(){
		Goods_du g = null;
		hot_sales.clear();
		try{
			String sql = "select * from goods order by sales DESC;";
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            for(int i=0;i<10 && rs.next();i++){
                g=new Goods_du();
	            g.setIndex(rs.getInt(1));
	            g.setName(rs.getString(2));
	            g.setPrice(rs.getDouble(3));
	            g.setStock(rs.getInt(4));
	            g.setShop(rs.getInt(5));
	            g.setDescription(rs.getString(6));
	            g.setSales(rs.getInt(7));
	            g.setRate(rs.getDouble(8));
	            g.setRateNum(rs.getInt(9));
                String temp = rs.getString(10);
                if(temp.equals("")==false){
                    String[] c = temp.split(",");
                    for(String x : c){
                    	int index = Integer.valueOf(x);
                    	Comments_du cc = get_comment(index);
                    	g.addComment(cc);
                    }
                }
                hot_sales.add(g);
            }
            rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return hot_sales.size();
	}
	
	public static int add_comment(String comment){
		int flag = -1;
		try{
			String sql = "insert into comments(date, author, comment) values (?,?,?)";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            pstmt.setString(1, dateString);
            pstmt.setString(2, Consumer_du.getName());
            pstmt.setString(3, comment);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            	flag = rs.getInt(1);
            pstmt.close();
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	}
	/**
	 * 新建订单（买家）
	 * @param order
	 * @return 订单在数据库的id
	 */
	public static int add_order(Order_du order){
		int flag = -1;
		try{
			String sql = "insert into orders(date, goods_id, goods_num, total_price) values (?,?,?,?)";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, order.getDate());
            String id = new String();
            String num = new String();
            for(CartNode_du i:Consumer_du.getCart().getCartList()){
            	id += i.getGoodIndex();
            	id += ",";
            	num += i.getNum();
            	num += ",";
            }
            pstmt.setString(2, id);
            pstmt.setString(3, num);
            pstmt.setDouble(4, order.getTotalPrice());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            	flag = rs.getInt(1);
            pstmt.close();
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	}
	/**
	 * 新建订单（卖家）
	 * @param order
	 * @return 订单在数据库的id
	 */
	public static int add_order_for_seller(Order_du order){
		int flag = -1;
		try{
			String sql = "insert into shop_orders(buyer, address, goods_id, goods_num, date, total_price)"
					+ " values (?,?,?,?,?,?)";
			PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, order.getBuyer());
            pstmt.setString(2, order.getAddress());
            String id = new String();
            String num = new String();
            for(CartNode_du i:order.getOrderList()){
            	id += i.getGoodIndex();
            	id += ",";
            	num += i.getNum();
            	num += ",";
            }
            pstmt.setString(3, id);
            pstmt.setString(4, num);
            pstmt.setString(5, order.getDate());
            pstmt.setDouble(6, order.getTotalPrice());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            	flag = rs.getInt(1);
            pstmt.close();
            rs.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
		return flag;
	}
	/**
	 * 把订单增加到店铺
	 * @param shopid
	 * @param orderid
	 */
	public static void add_order_to_shop(int shopid, int orderid){
		String exist_orders = null;
		try{
			String sql = "select orders from shops where id="+shopid;
			Statement stmt = conn.createStatement();
			PreparedStatement pstmt;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				exist_orders = rs.getString(1);
			sql = "update shops set orders=? where id=" + shopid;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, orderid + "," +exist_orders);
			pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	/**
	 * 增加店铺销售额
	 * @param shopid
	 * @param money
	 */
	public static void add_sales_to_shop(int shopid, double money){
		Double sales = 0.0;
		try{
			String sql = "select sales from shops where id="+shopid;
			Statement stmt = conn.createStatement();
			PreparedStatement pstmt;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				sales = rs.getDouble(1);
			sql = "update shops set sales=? where id=" + shopid;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setDouble(1, sales+money);
			pstmt.executeUpdate();
            pstmt.close();
		}
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void remove_comment(int id, String comment){
		//TODO
	}
}
