package du.entity;

import du.helping.Comments_du;

import java.util.ArrayList;
import java.util.List;

/**
 * ��Ʒ��
 * Created by ShuhanShen on 07��10��17.
 */
public class Goods_du {
    private String name; //��Ʒ����
    private double price; //��Ʒ�۸�
    private List<Comments_du> comments = new ArrayList(); //��Ʒ�����б�
    private int index;  //���ݿ���ID
    private int shop;  //��������
    private String description; //��Ʒ����
    private int stock; // ��Ʒ���
    private int inCart; //����Ʒ�ڹ��ﳵ�������
    private int sales; // ������
    private double rate; // ��Ʒ���
    private int rateNum; // ��Ʒ�������

    public Goods_du(){}
    public Goods_du(String name, double price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.inCart = 0;
    }

    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Comments_du> getComments() {
        return comments;
    }

    public void setComments(List<Comments_du> comments) {
        this.comments = comments;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Ϊ��Ʒ�������
     * @param comment
     */
    public void addComment(Comments_du c){
    	comments.add(c);
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    /**
     * �û�Ϊ��Ʒ��֣�ͬʱ�����������
     * @param rate
     */
    public void Rate(double rate) {
        this.rate = (this.rate * rateNum + rate) / (this.rateNum + 1);
        this.rateNum = this.rateNum + 1;
    }
    
    public void add_sales(int num){
    	this.sales+=num;
    }
    
    public void in(int num){
    	this.stock+=num;
    }
    
    public void out(int num){
    	this.stock-=num;
    }
}
