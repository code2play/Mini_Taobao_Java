package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.entity.Order_du;
import du.helping.CartNode_du;
import du.role.Consumer_du;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;

public class ordersController {
	@FXML
	private Hyperlink hl0;
	@FXML
	private Hyperlink hl1;
	@FXML
	private Hyperlink hl2;
	@FXML
	private Hyperlink hl3;
	@FXML
	private Hyperlink hl4;
	@FXML
	private Hyperlink hl5;
	@FXML
	private Hyperlink hl6;
	@FXML
	private Hyperlink hl7;
	@FXML
	private Hyperlink hl8;
	@FXML
	private Hyperlink hl9;
	@FXML
	private Label l0;
	@FXML
	private Label l1;
	@FXML
	private Label l2;
	@FXML
	private Label l3;
	@FXML
	private Label l4;
	@FXML
	private Label l5;
	@FXML
	private Label l6;
	@FXML
	private Label l7;
	@FXML
	private Label l8;
	@FXML
	private Label l9;
	@FXML
	private Button goback;
	@FXML
	private Button pageup;
	@FXML
	private Button pagedown;
	
	private Map<Integer, Hyperlink> hl = new HashMap<Integer, Hyperlink>();
	private Map<Integer, Label> lb = new HashMap<Integer, Label>();
	private List<Integer> glist = new ArrayList<Integer>();
	private List<Integer> nlist = new ArrayList<Integer>();
	private List<Integer> eof_order = new ArrayList<Integer>();

	private Mgr_du mgr = new Mgr_du();
	
	private int page=1, pagevolume=10, num;
	
	public void initialize(){
		StageMgr.go_back_to_orders = true;
		glist.clear();
		nlist.clear();
		eof_order.clear();
		num = Consumer_du.getOrders().size();
		hl.put(0, hl0);	lb.put(0, l0);
		hl.put(1, hl1);	lb.put(1, l1);
		hl.put(2, hl2);	lb.put(2, l2);
		hl.put(3, hl3);	lb.put(3, l3);
		hl.put(4, hl4);	lb.put(4, l4);
		hl.put(5, hl5);	lb.put(5, l5);
		hl.put(6, hl6);	lb.put(6, l6);
		hl.put(7, hl7);	lb.put(7, l7);
		hl.put(8, hl8);	lb.put(8, l8);
		hl.put(9, hl9);	lb.put(9, l9);
		
		pageup.setDisable(true);
		pagedown.setDisable(true);
	
		for(Order_du o : Consumer_du.getOrders()){
			for(CartNode_du c : o.getOrderList()){
				glist.add(c.getGoodIndex());
				nlist.add(c.getNum());
			}
			eof_order.add(glist.size()-1);
		}
		num = glist.size();
		this.refresh();
	}
	
	public void refresh(){
		if(page==1) pageup.setDisable(true);
		else pageup.setDisable(false);
		if(Math.ceil(1.0*num/pagevolume)==page) pagedown.setDisable(true);
		else pagedown.setDisable(false);
		if(num==0) pagedown.setDisable(true);
		
		for(int i=(page-1)*10;i<page*10;i++){
			Hyperlink h = hl.get(i%10);
			Label l = lb.get(i%10);
			Goods_du g = null;
			h.setVisited(false);
			if(i>=num){
				h.setText("");
				h.setVisible(false);
				l.setText("");
				l.setVisible(false);
			} else {
				g = Mgr_du.get_goods(glist.get(i));
				if(g==null){
					h.setVisible(true);
					h.setText("商品消失了");
					l.setVisible(true);
				}else{
					h.setVisible(true);
					h.setText(g.getName());
					String temp = new String("单价:"+g.getPrice()+"元\t\t数量:"+nlist.get(i));
					for(int j=0;j<eof_order.size();j++){
						if(i==eof_order.get(j)){
							temp+="\t";
							temp+=Consumer_du.getOrders().get(j).getDate();
							temp+="\t总价:";
							temp+=Consumer_du.getOrders().get(j).getTotalPrice();
						}
					}
					l.setVisible(true);
					l.setText(temp);
				}
			}
		}
		if(num==0) {
			l0.setText("还没购买过任何商品呢，快去下单吧");
			l0.setVisible(true);
		}
	}

	// Event Listener on Button[#goback].onAction
	@FXML
	public void Goback(ActionEvent event) {
		StageMgr.go_back_to_orders = false;
		StageMgr.STAGES.get("Orders").close();
		StageMgr.STAGES.remove("Orders");
		StageMgr.STAGES.get("Consumer_main").show();
	}
	
	public void show() throws IOException{
		//StageMgr.type = "others";
		Parent root = FXMLLoader.load(getClass().getResource("goods.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Goods");
		StageMgr.STAGES.put("Goods", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Orders").close();
	}
	// Event Listener on Hyperlink[#hl0].onAction
	@FXML
	public void hl0c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 0);
		this.show();
	}
	// Event Listener on Hyperlink[#hl1].onAction
	@FXML
	public void hl1c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 1);
		this.show();
	}
	// Event Listener on Hyperlink[#hl2].onAction
	@FXML
	public void hl2c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 2);
		this.show();
	}
	// Event Listener on Hyperlink[#hl3].onAction
	@FXML
	public void hl3c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 3);
		this.show();
	}
	// Event Listener on Hyperlink[#hl4].onAction
	@FXML
	public void hl4c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 4);
		this.show();
	}
	// Event Listener on Hyperlink[#hl5].onAction
	@FXML
	public void hl5c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 5);
		this.show();
	}
	// Event Listener on Hyperlink[#hl6].onAction
	@FXML
	public void hl6c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 6);
		this.show();
	}
	// Event Listener on Hyperlink[#hl7].onAction
	@FXML
	public void hl7c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 7);
		this.show();
	}
	// Event Listener on Hyperlink[#hl8].onAction
	@FXML
	public void hl8c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 8);
		this.show();
	}
	// Event Listener on Hyperlink[#hl9].onAction
	@FXML
	public void hl9c(ActionEvent event) throws IOException {
		StageMgr.id_in_db = glist.get((page-1)*10 + 9);
		this.show();
	}
	// Event Listener on Button[#pageup].onAction
	@FXML
	public void Pageup(ActionEvent event) {
		page--;
		this.refresh();
	}
	// Event Listener on Button[#pagedown].onAction
	@FXML
	public void Pagedown(ActionEvent event) {
		page++;
		this.refresh();
	}
}
