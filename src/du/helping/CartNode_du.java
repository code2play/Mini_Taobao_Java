package du.helping;

/**
 * ��Ϊ���ﳵ�б����һ�����嵥Ԫ��������Ʒ������
 * Created by ShuhanShen on 07��10��17.
 */
public class CartNode_du {
    private int goodIndex;
    private int num;

    public CartNode_du(){}
    public CartNode_du(int goodIndex, int num) {
        this.goodIndex = goodIndex;
        this.num = num;
    }

    public int getGoodIndex() {
        return goodIndex;
    }

    public void setGoodIndex(int goodIndex) {
        this.goodIndex = goodIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public void add(int num){
    	this.num+=num;
    }
}
