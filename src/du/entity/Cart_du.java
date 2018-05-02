package du.entity;

import du.biz.Mgr_du;
import du.helping.CartNode_du;
import du.role.Consumer_du;
import du.role.Seller_du;
import du.ui.StageMgr;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ���ﳵ��
 * Created by ��κ�+������ on 07��10��17.
 */
public class Cart_du {

    private List<CartNode_du> cartList = new ArrayList();
    private Mgr_du mgr = new Mgr_du();

    public List<CartNode_du> getCartList() {
        return cartList;
    }

    /**
     * �����Ʒ���빺�ﳵ����Ʒ�±�INDEX,����NUM
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

        //���ڹ��ﳵ��
        System.out.println(num);
        System.out.println(tmp.getStock());
        if (num <= tmp.getStock()) { //num ��������
            CartNode_du temp = new CartNode_du(index, num);
            cartList.add(temp);
            return true;
        } else
        	return false;
    }

    /**
     * ͨ��INDEX���ҹ����б����Ʒ����������
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
//     * ͨ�����ɾ����Ʒ
//     * @param index
//     */
//    public void removeGoods(int index) {
//        boolean foundInList = false;  //�Ƿ��ҵ���Ӧ����Ʒ
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
////            ������ʾ
////            System.out.println("û���ҵ���Ӧ��Ʒ");
//        }
//    }

    /**
     * ͨ������޸���Ʒ����
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
                    if (num > prenum) { //���ڵ���������֮ǰ���������������Ӻ���
                        return addGoods(index, num - prenum);
                    }
                    else if (num < prenum){ //���ڵ�����С��֮ǰ������������ü��ٺ���
                        return decreaseGoods(index, prenum - num);
                    }
                }
            }
        }

        return false;
    }

    /**
     * ���㹺�ﳵ����Ʒ�ܼ�
     * @return
     */
    public double pay(List<CartNode_du> CartList) {
        double totalPay = 0;
        for  (CartNode_du x : CartList) {
            //ͨ��INDEX������Ʒ
            int index = x.getGoodIndex();
            Goods_du tmp = Mgr_du.get_goods(index);
            totalPay += tmp.getPrice() * x.getNum();
        }
        return totalPay;
    }

    /**
     * ���㹺�ﳵ
     */
    public String checkOut() {
    	if(this.cartList.size()==0)
    		return "���ﳵ�տ���Ҳ�أ���ȥ���һЩ��Ʒ��";
        Order_du tmp = new Order_du();
        List<CartNode_du> tmpList = new ArrayList<CartNode_du>();
    	List<Goods_du> glist = new ArrayList<Goods_du>();
        List<List<CartNode_du>> tmpShopList = new ArrayList<>(); //�̼�������ʱ�б�
        Map<Integer, Integer> mp = new HashMap<>(); //ӳ���̼�ID����ʱ�б�λ��
        Map<Integer, Integer> mp2 = new HashMap<>(); //ӳ����ʱ�б�λ�����̼�ID
        Set<Integer> ShopIdSet = new HashSet<Integer>();
    	
        for(CartNode_du x : this.cartList){
        	tmpList.add(x);
        	Goods_du g = new Goods_du();
        	g = Mgr_du.get_goods(x.getGoodIndex());
        	if(g.getStock()<x.getNum()){
        		String ans = new String("����ʧ�ܣ���Ʒ ");
        		ans+=g.getName();
        		ans+=" ��治��";
        		return ans;
        	} else glist.add(g);
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        tmp.setDate(dateString);
        tmp.setOrderList(tmpList);
        tmp.setTotalPrice(pay(cartList));

        if (tmp.getTotalPrice() <= Consumer_du.getMoney()) { //���
            int i=0;
            for(CartNode_du x : cartList){

                /*******************************
                 * ������ͳ����Ҷ���
                 */

            	Goods_du g = glist.get(i);
            	g.out(x.getNum());
            	g.add_sales(x.getNum());
            	Mgr_du.save_goods(g);

                /*******************************
                 * ������ͳ�����Ҷ���
                 */

                int shopId = g.getShop();
                ShopIdSet = mp.keySet();
                if (ShopIdSet.contains(Integer.valueOf(shopId)) == false) { //���ӳ���в������̼�ID�����½�ӳ�䣬�½�����
                    int tmpa = shopId, tmpb = tmpShopList.size();
                    mp.put(Integer.valueOf(tmpa), Integer.valueOf(tmpb));  //�������ݿ�ID ��> �����±��ӳ��
                    mp2.put(Integer.valueOf(tmpb), Integer.valueOf(tmpa)); //���������±� -> ���ݿ�ID��ӳ��
                    tmpShopList.add(new ArrayList<CartNode_du>());         //�����µ�����ռ�
                }
                int listID = mp.get(Integer.valueOf(shopId));
                tmpShopList.get(listID).add(x);                  //�����ﳵ�����Ϣ�����̼ҷ��࣬�����Ӧ������


                /********************************/

                i ++;

            }
            /************************************
             * ����ÿһ���������е�Ԫ�أ����һ��ORDER�࣬�����̼�
             */

            for (List<CartNode_du> x : tmpShopList) {
                Order_du tmpForSeller = new Order_du();
                tmpForSeller.setDate(dateString);
                tmpForSeller.setOrderList(x);
                tmpForSeller.setBuyer(Consumer_du.getName());          //���ö������������
                tmpForSeller.setAddress(Consumer_du.getAddress());     //���ö�������ҵ�ַ
                double salesForSeller = pay(x);
                tmpForSeller.setTotalPrice(salesForSeller);
                int orderIdForSeller = Mgr_du.add_order_for_seller(tmpForSeller); //�����ݿ�����Ӷ���
                tmpForSeller.setIndex(orderIdForSeller);
                Mgr_du.add_order_to_shop(mp2.get(tmpShopList.indexOf(x)), orderIdForSeller); //Ϊ������Ӷ���
                Mgr_du.add_sales_to_shop(mp2.get(tmpShopList.indexOf(x)), salesForSeller);   //Ϊ�������Ӫҵ��
                System.out.println(tmpShopList.indexOf(x));
            }

            /************************************/


            int orderid = Mgr_du.add_order(tmp);
            tmp.setIndex(orderid);

        	Consumer_du.add_order(tmp);
            Consumer_du.getCart().cartList.clear();
            Consumer_du.consume(tmp.getTotalPrice());
            Mgr_du.save_consumer();
            return "����ɹ�";
        }
        else return "����ʧ�ܣ�����";
    }
}
