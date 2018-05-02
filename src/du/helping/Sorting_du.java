package du.helping;

import du.biz.Mgr_du;
import du.entity.Goods_du;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.StrictMath.acos;
import static java.lang.StrictMath.tan;

/**
 * 用于对所有商品的搜索的排序，店铺内搜索商品的排序
 * 辅助功能
 * Created by ShuhanShen on 07／14／17.
 */
public class Sorting_du {

    public static List<Goods_du> resultList = new ArrayList<Goods_du>();
    public static String type = null;

    public static void init(){
    	resultList.clear();
    	if(type.equals("search res")){
            for (Goods_du x : Mgr_du.goods_res)
                resultList.add(x);
    	}else if(type.equals("hot sales")){
            for (Goods_du x : Mgr_du.hot_sales)
                resultList.add(x);
    	}else if(type.equals("goods from shop")){
            for (Goods_du x : Mgr_du.goods_from_shop)
                resultList.add(x);
    	}
    }
    
    /**
     * 对所有商品按评分进行排序，排序结果保存在RESULTLIST中
     */
    public static void SortbyRate(int dir) {
        init();

        Collections.sort(resultList, new Comparator<Goods_du>() {
            public int compare(Goods_du ob1, Goods_du ob2) {
                if (ob1.getRate() > ob2.getRate()) return dir;
                else return -dir;
            }
        });
    }

    /**
     * 对所有商品按销量排序，排序结果在RESULTLIST中
     */
    public static void SortbySales(int dir) {
        init();

        Collections.sort(resultList, new Comparator<Goods_du>() {
            public int compare(Goods_du ob1, Goods_du ob2) {
                if (ob1.getSales() > ob2.getSales()) return dir;
                else return -dir;
            }
        });
    }
    
    /**
     * 对所有商品按价格排序，排序结果在RESULTLIST中
     */
    public static void SortbyPrice(int dir) {
        init();

        Collections.sort(resultList, new Comparator<Goods_du>() {
            public int compare(Goods_du ob1, Goods_du ob2) {
                if (ob1.getPrice() > ob2.getPrice()) return dir;
                else return -dir;
            }
        });
    }

    /**
     * 对所有商品默认排序，按ahp原理分配权重，对数据归一化处理后加权排序，排序结果在RESULTLIST中
     */
    public static void DefaultSort() {
        init();
        
        Collections.sort(resultList, new Comparator<Goods_du>() {
            public int compare(Goods_du ob1, Goods_du ob2) {
                double temp1=tan(ob1.getPrice())*acos(-1)/2*0.4
                			+tan(ob1.getSales())*acos(-1)/2*0.3
                			+tan(ob1.getRate() )*acos(-1)/2*0.3;
                double temp2=tan(ob1.getPrice())*acos(-1)/2*0.4
                			+tan(ob2.getSales())*acos(-1)/2*0.3
                			+tan(ob2.getRate() )*acos(-1)/2*0.3;
                if(temp1>temp2)return 1;
                else return -1;
            }
        });
    }
}
