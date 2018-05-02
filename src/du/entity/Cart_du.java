package du.entity;

import du.biz.Mgr_du;
import du.helping.CartNode_du;
import du.role.Consumer_du;
import du.role.Seller_du;
import du.ui.StageMgr;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 购物车类
 * Created by 田嘉豪+沈书晗 on 07／10／17.
 */
public class Cart_du {

    private List<CartNode_du> cartList = new ArrayList();
    private Mgr_du mgr = new Mgr_du();

    public List<CartNode_du> getCartList() {
        return cartList;
    }

    /**
     * 添加商品进入购物车，商品下标INDEX,数量NUM
     * @param index
     * @param num
     */
    public boolean addGoods(int index, int num) {
    	if(num<0) return false;
    	Goods_du tmp = Mgr_du.get_goods(index);
    	
        for (CartNode_du x : cartList) {
            if (x.getGoodIndex() == index) {
                x.add(num);
                if(tmp.getStock()>=x.getNum())
                	return true;
                else{
                	x.add(-num);
                	return false;
                }
            }
        }

        //不在购物车中
        System.out.println(num);
        System.out.println(tmp.getStock());
        if (num <= tmp.getStock()) { //num 符合条件
            CartNode_du temp = new CartNode_du(index, num);
            cartList.add(temp);
            return true;
        } else
        	return false;
    }

    /**
     * 通过INDEX查找购物列表的物品，减少数量
     * @param index
     * @param num
     */
    public boolean decreaseGoods(int index, int num) {
        Goods_du tmp = Mgr_du.get_goods(index);
        for (CartNode_du x : cartList) {
            if (x.getGoodIndex() == index) {
                x.add(-num);
                if(x.getNum()>0)
                	return true;
                else if(x.getNum()==0) {
                	cartList.remove(x);
                	break;
                }
                else{
                	x.add(num);
                	return false;
                }
            }
        }

        return false;
    }

//    /**
//     * 通过标号删除物品
//     * @param index
//     */
//    public void removeGoods(int index) {
//        boolean foundInList = false;  //是否找到对应的物品
//        for (CartNode_du x : cartList) {
//            if (x.getGoodIndex() == index) {
//                foundInList = true;
//                break;
//            }
//        }
//
//        if (foundInList) {
//            for (CartNode_du x : cartList) {
//                if (x.getGoodIndex() == index) {
//                    cartList.remove(x);
//                    break;
//                }
//            }
//        }
//        else {
////            错误提示
////            System.out.println("没有找到对应物品");
//        }
//    }

    /**
     * 通过标号修改物品数量
     * @param index
     * @param num
     */
    public boolean changeNum(int index, int num) {
        boolean foundInList = false;
        for  (CartNode_du x : cartList) {
            if (x.getGoodIndex() == index) {
                foundInList = true;
                break;
            }
        }

        if (foundInList) {
            for (CartNode_du x : cartList) {
                if (x.getGoodIndex() == index) {
                    int prenum = x.getNum();
//                    if (num == 0) {
//                        cartList.remove(x);
//                        return true;
//                    }
                    if (num > prenum) { //现在的数量大于之前的数量，则调用添加函数
                        return addGoods(index, num - prenum);
                    }
                    else if (num < prenum){ //现在的数量小于之前的数量，则调用减少函数
                        return decreaseGoods(index, prenum - num);
                    }
                }
            }
        }

        return false;
    }

    /**
     * 计算购物车内商品总价
     * @return
     */
    public double pay(List<CartNode_du> CartList) {
        double totalPay = 0;
        for  (CartNode_du x : CartList) {
            //通过INDEX查找物品
            int index = x.getGoodIndex();
            Goods_du tmp = Mgr_du.get_goods(index);
            totalPay += tmp.getPrice() * x.getNum();
        }
        return totalPay;
    }

    /**
     * 结算购物车
     */
    public String checkOut() {
    	if(this.cartList.size()==0)
    		return "购物车空空如也呢，快去添加一些商品吧";
        Order_du tmp = new Order_du();
        List<CartNode_du> tmpList = new ArrayList<CartNode_du>();
    	List<Goods_du> glist = new ArrayList<Goods_du>();
        List<List<CartNode_du>> tmpShopList = new ArrayList<>(); //商家销售临时列表
        Map<Integer, Integer> mp = new HashMap<>(); //映射商家ID与临时列表位置
        Map<Integer, Integer> mp2 = new HashMap<>(); //映射临时列表位置与商家ID
        Set<Integer> ShopIdSet = new HashSet<Integer>();
    	
        for(CartNode_du x : this.cartList){
        	tmpList.add(x);
        	Goods_du g = new Goods_du();
        	g = Mgr_du.get_goods(x.getGoodIndex());
        	if(g.getStock()<x.getNum()){
        		String ans = new String("结算失败，商品 ");
        		ans+=g.getName();
        		ans+=" 库存不足";
        		return ans;
        	} else glist.add(g);
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        tmp.setDate(dateString);
        tmp.setOrderList(tmpList);
        tmp.setTotalPrice(pay(cartList));

        if (tmp.getTotalPrice() <= Consumer_du.getMoney()) { //余额
            int i=0;
            for(CartNode_du x : cartList){

                /*******************************
                 * 以下是统计买家订单
                 */

            	Goods_du g = glist.get(i);
            	g.out(x.getNum());
            	g.add_sales(x.getNum());
            	Mgr_du.save_goods(g);

                /*******************************
                 * 以下是统计卖家订单
                 */

                int shopId = g.getShop();
                ShopIdSet = mp.keySet();
                if (ShopIdSet.contains(Integer.valueOf(shopId)) == false) { //如果映射中不存在商家ID，则新建映射，新建数组
                    int tmpa = shopId, tmpb = tmpShopList.size();
                    mp.put(Integer.valueOf(tmpa), Integer.valueOf(tmpb));  //建立数据库ID －> 数组下标的映射
                    mp2.put(Integer.valueOf(tmpb), Integer.valueOf(tmpa)); //建立数组下标 -> 数据库ID的映射
                    tmpShopList.add(new ArrayList<CartNode_du>());         //开辟新的数组空间
                }
                int listID = mp.get(Integer.valueOf(shopId));
                tmpShopList.get(listID).add(x);                  //将购物车里的信息按照商家分类，放入对应的数组


                /********************************/

                i ++;

            }
            /************************************
             * 对于每一个在数组中的元素，添加一个ORDER类，放入商家
             */

            for (List<CartNode_du> x : tmpShopList) {
                Order_du tmpForSeller = new Order_du();
                tmpForSeller.setDate(dateString);
                tmpForSeller.setOrderList(x);
                tmpForSeller.setBuyer(Consumer_du.getName());          //设置订单的买家姓名
                tmpForSeller.setAddress(Consumer_du.getAddress());     //设置订单的买家地址
                double salesForSeller = pay(x);
                tmpForSeller.setTotalPrice(salesForSeller);
                int orderIdForSeller = Mgr_du.add_order_for_seller(tmpForSeller); //在数据库里添加订单
                tmpForSeller.setIndex(orderIdForSeller);
                Mgr_du.add_order_to_shop(mp2.get(tmpShopList.indexOf(x)), orderIdForSeller); //为店铺添加订单
                Mgr_du.add_sales_to_shop(mp2.get(tmpShopList.indexOf(x)), salesForSeller);   //为店铺添加营业额
                System.out.println(tmpShopList.indexOf(x));
            }

            /************************************/


            int orderid = Mgr_du.add_order(tmp);
            tmp.setIndex(orderid);

        	Consumer_du.add_order(tmp);
            Consumer_du.getCart().cartList.clear();
            Consumer_du.consume(tmp.getTotalPrice());
            Mgr_du.save_consumer();
            return "结算成功";
        }
        else return "结算失败，余额不足";
    }
}
