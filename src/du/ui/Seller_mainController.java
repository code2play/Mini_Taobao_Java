package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.role.Seller_du;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.Hyperlink;

public class Seller_mainController {
	@FXML
	private TextField name;
	@FXML
	private TextField sales;
	@FXML
	private TextArea description;
	@FXML
	private Button modify;
	@FXML
	private Button save;
	@FXML
	private Button add_goods;
	@FXML
	private Button orders;
	@FXML
	private Button logout;
	@FXML
	private Label create_label;
	@FXML
	private Button create;
	@FXML
	private Label label;
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
	private Button pageup;
	@FXML
	private Button pagedown;
	@FXML
	private Label text;

	private Mgr_du mgr = new Mgr_du();
	
	private Map<Integer, Hyperlink> hl = new HashMap<Integer, Hyperlink>();
	private Map<Integer, Label> lb = new HashMap<Integer, Label>();
	
	private int page=1, pagevolume=10, num=0;
	
	public void init(){
		name.setEditable(false);
		description.setEditable(false);
		sales.setDisable(false);
		sales.setEditable(false);
		name.setText(Seller_du.getShop().getName());
		description.setText(Seller_du.getShop().getDescription());
		Double x = new Double(Seller_du.getShop().getSales());
		sales.setText(x.toString());
		create.setVisible(false);
		create_label.setVisible(false);
		label.setText("");
		modify.setDisable(false);
		save.setDisable(true);
		add_goods.setDisable(false);
	}
	
	public void initialize(){
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
		
		if(Seller_du.getShop()==null){
			name.setEditable(true);
			description.setEditable(true);
			sales.setDisable(true);
			modify.setDisable(true);
			save.setDisable(true);
			add_goods.setDisable(true);
			num = 0;
			this.refresh();
		}else{
			this.init();
			num = mgr.get_goods_from_shop(Seller_du.getShop().getIndex());
			this.refresh();
		}
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
				g = Mgr_du.goods_from_shop.get(i);
				h.setVisible(true);
				h.setText(g.getName());
				String temp = new String(g.getPrice()+"元\t\t\t销量:"+g.getSales());
				l.setVisible(true);
				l.setText(temp);
			}
		}
		if(num==0) text.setText("赶快添加一些商品吧!");
	}
	
	// Event Listener on Button[#modify].onAction
	@FXML
	public void modify(ActionEvent event) {
		modify.setDisable(true);
		save.setDisable(false);
		name.setEditable(true);
		description.setEditable(true);
	}
	// Event Listener on Button[#save].onAction
	@FXML
	public void save(ActionEvent event) {
		String n = name.getText();
		String d = description.getText();
		if(n.equals("")){
			label.setText("店铺名称不能为空");
			return;
		}
		Seller_du.getShop().setName(n);
		Seller_du.getShop().setDescription(d);
		Mgr_du.save_shop();
		this.init();
	}
	// Event Listener on Button[#add_goods].onAction
	@FXML
	public void add_goods(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("new_goods.fxml"));
		Stage stage = new Stage();
		stage.setTitle("New_goods");
		StageMgr.STAGES.put("New_goods", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Seller_main").close();
	}
	// Event Listener on Button[#logout].onAction
	@FXML
	public void logout(ActionEvent event) {
		StageMgr.STAGES.get("Seller_main").close();
		StageMgr.STAGES.remove("Seller_main");
		StageMgr.STAGES.get("Login").show();
	}
	// Event Listener on Button[#create].onAction
	@FXML
	public void create_shop(ActionEvent event) {
		String n = name.getText();
		String d = description.getText();
		if(n.equals("")){
			label.setText("店铺名称不能为空");
			return;
		}
		int shop = Mgr_du.add_shop(n, d);
		if(shop>0) Seller_du.setShop(Mgr_du.get_shop(shop));
		//System.out.println(shop);
		Mgr_du.save_seller();
		this.init();
		this.refresh();
	}
	
	public void show() throws IOException{
		//StageMgr.type = "shop";
		Parent root = FXMLLoader.load(getClass().getResource("goods_seller.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Goods_seller");
		StageMgr.STAGES.put("Goods_seller", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Seller_main").close();
	}
	// Event Listener on Hyperlink[#hl0].onAction
	@FXML
	public void hl0c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 0;
		this.show();
	}
	// Event Listener on Hyperlink[#hl1].onAction
	@FXML
	public void hl1c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 1;
		this.show();
	}
	// Event Listener on Hyperlink[#hl2].onAction
	@FXML
	public void hl2c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 2;
		this.show();
	}
	// Event Listener on Hyperlink[#hl3].onAction
	@FXML
	public void hl3c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 3;
		this.show();
	}
	// Event Listener on Hyperlink[#hl4].onAction
	@FXML
	public void hl4c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 4;
		this.show();
	}
	// Event Listener on Hyperlink[#hl5].onAction
	@FXML
	public void hl5c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 5;
		this.show();
	}
	// Event Listener on Hyperlink[#hl6].onAction
	@FXML
	public void hl6c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 6;
		this.show();
	}
	// Event Listener on Hyperlink[#hl7].onAction
	@FXML
	public void hl7c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 7;
		this.show();
	}
	// Event Listener on Hyperlink[#hl8].onAction
	@FXML
	public void hl8c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 8;
		this.show();
	}
	// Event Listener on Hyperlink[#hl9].onAction
	@FXML
	public void hl9c(ActionEvent event) throws IOException {
		StageMgr.index = (page-1)*10 + 9;
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
	// Event Listener on Button[#orders].onAction
	@FXML
	public void Orders(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("seller_sellingrecords.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Selling Records");
		StageMgr.STAGES.put("Selling_records", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Seller_main").close();
	}
}
