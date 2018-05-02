package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.entity.Shop_du;
import du.helping.Sorting_du;
import du.role.Consumer_du;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.Hyperlink;

public class Consumer_mainController {
	@FXML
	private Label label;
	@FXML
	private Button logout;
	@FXML
	private TextField name;
	@FXML
	private TextField address;
	@FXML
	private TextField money;
	@FXML
	private Button modify;
	@FXML
	private Button recharge;
	@FXML
	private Button save;
	@FXML
	private TextField MONEY;
	@FXML
	private TextField key_words;
	@FXML
	private Button search2;
	@FXML
	private Button search1;
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
	private Button hot_sale;
	@FXML
	private Button pageup;
	@FXML
	private Button pagedown;
	@FXML
	private Label text;
	@FXML
	private Button show_cart;
	@FXML
	private Button orders;
	@FXML
	private Button ds;
	@FXML
	private Button ps;
	@FXML
	private Button ss;
	@FXML
	private Button rs;


	private Map<Integer, Hyperlink> hl = new HashMap<Integer, Hyperlink>();
	private Map<Integer, Label> lb = new HashMap<Integer, Label>();

	private Mgr_du mgr = new Mgr_du();
	
	private int page=1, pagevolume=10, num, dir1=1, dir2=1, dir3=1;
	
	public void init(){
		name.setText(Consumer_du.getName());
		address.setText(Consumer_du.getAddress());
		Double m = new Double(Consumer_du.getMoney());
		money.setText(m.toString());
		modify.setDisable(false);
		save.setDisable(true);
		recharge.setDisable(false);
		name.setEditable(false);
		address.setEditable(false);
		money.setEditable(false);
		key_words.setEditable(true);
		search1.setDisable(false);
		search2.setDisable(true);
		label.setText("");
		MONEY.setText("");
	}
	
	public void initialize(){
		this.init();
		if(Consumer_du.getAddress().equals("")){
			label.setText("请填写收货地址");
			save.setDisable(false);
			modify.setDisable(true);
			address.setEditable(true);
			key_words.setEditable(false);
			search1.setDisable(true);
			search2.setDisable(true);
			show_cart.setDisable(true);
		}

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
		
		Sorting_du.type="hot sales";
		num = mgr.get_hot_sales();
		Sorting_du.DefaultSort();
		this.refresh_goods();
		text.setText("以下是当前热销商品");
	}
	
	public void refresh_goods(){
		if(page==1) pageup.setDisable(true);
		else pageup.setDisable(false);
		if(Math.ceil(1.0*num/pagevolume)==page) pagedown.setDisable(true);
		else pagedown.setDisable(false);
		
		for(int i=(page-1)*pagevolume;i<page*pagevolume;i++){
			Hyperlink h = hl.get(i%pagevolume);
			Label l = lb.get(i%pagevolume);
			Goods_du g = null;
			h.setVisited(false);
			if(i>=num){
				h.setText("");
				h.setVisible(false);
				l.setText("");
				l.setVisible(false);
			} else {
				g = (Goods_du) Sorting_du.resultList.get(i);
				h.setVisible(true);
				h.setText(g.getName());
				String temp = new String(g.getPrice()+"元\t\t\t销量:"+g.getSales());
				DecimalFormat df = new DecimalFormat("#.00");
				temp += "\t\t评分:" + df.format(g.getRate());
				l.setVisible(true);
				l.setText(temp);
			}
		}
		if(num==0) text.setText("没有找到相关商品");
	}
	
	public void refresh_shops(){
		if(page==1) pageup.setDisable(true);
		else pageup.setDisable(false);
		if(Math.ceil(1.0*num/pagevolume)==page) pagedown.setDisable(true);
		else pagedown.setDisable(false);
		
		for(int i=(page-1)*pagevolume;i<page*pagevolume;i++){
			Hyperlink h = hl.get(i%pagevolume);
			Label l = lb.get(i%pagevolume);
			h.setVisited(false);
			if(i>=num){
				h.setText("");
				h.setVisible(false);
				l.setText("");
				l.setVisible(false);
			} else {
				Shop_du s = Mgr_du.shops_res.get(i);
				h.setVisible(true);
				h.setText(s.getName());
				l.setVisible(true);
				l.setText(s.getDescription());
			}
		}
		if(num==0) text.setText("没有找到相关店铺");
	}
	
	// Event Listener on Button[#logout].onAction
	@FXML
	public void logout(ActionEvent event) throws SQLException{
		StageMgr.STAGES.get("Consumer_main").close();
		StageMgr.STAGES.remove("Consumer_main");
		StageMgr.STAGES.get("Login").show();
	}
	
	// Event Listener on Button[#modify].onAction
	@FXML
	public void modify(ActionEvent event) {
		save.setDisable(false);
		modify.setDisable(true);
		name.setEditable(true);
		address.setEditable(true);
	}
	// Event Listener on Button[#recharge].onAction
	@FXML
	public void recharge(ActionEvent event) {
		if(MONEY.getText().equals("")){
			label.setText("请输入充值金额");
			return;
		}
		double m = Double.valueOf(MONEY.getText());
		if(m>0.0){
			Consumer_du.recharge(m);
			Mgr_du.save_consumer();
			this.init();
		} else {
			label.setText("充值金额必须大于零");
		}
	}
	// Event Listener on Button[#save].onAction
	@FXML
	public void save(ActionEvent event) {
		String n = name.getText();
		String a = address.getText();
		if(n.equals("") || a.equals("")){
			label.setText("昵称或收货地址不能为空");
			return;
		}
		Consumer_du.setName(n);
		Consumer_du.setAddress(a);
		Mgr_du.save_consumer();
		this.init();
	}
	// Event Listener on Button[#search1].onAction
	@FXML
	public void search_goods(ActionEvent event) {
		String kw = key_words.getText();
		if(kw.equals("")){
			num = 0;
			//this.refresh_goods();
			text.setText("关键词不能为空");
		}
		else{
			num = mgr.search_goods(kw);
			Sorting_du.type = "search res";
			Sorting_du.DefaultSort();
			text.setText("以下为搜索\""+kw+"\"的结果");
			this.refresh_goods();
		}
	}
	// Event Listener on Button[#search2].onAction
	@FXML
	public void search_shops(ActionEvent event) {
		String kw = key_words.getText();
		if(kw.equals("")){
			num = 0;
			this.refresh_shops();
			text.setText("关键词不能为空");
		}
		else{
			//StageMgr.type = "shops";
			num = mgr.search_shops(kw);
			text.setText("以下为搜索\""+kw+"\"的结果");
			this.refresh_shops();
		}
	}
	
	public void show() throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("goods.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Goods");
		StageMgr.STAGES.put("Goods", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Consumer_main").close();
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
	// Event Listener on Button[#hot_sale].onAction
	@FXML
	public void Hot_sale(ActionEvent event) {
		num = mgr.get_hot_sales();
		Sorting_du.type = "hot sales";
		Sorting_du.DefaultSort();
		this.refresh_goods();
		pageup.setDisable(true);
		pagedown.setDisable(true);
		text.setText("以下是当前热销商品");
	}
	// Event Listener on Button[#pageup].onAction
	@FXML
	public void Pageup(ActionEvent event) {
		page--;
		this.refresh_goods();
	}
	// Event Listener on Button[#pagedown].onAction
	@FXML
	public void Pagedown(ActionEvent event) {
		page++;
		this.refresh_goods();
	}
	// Event Listener on Button[#show_cart].onAction
	@FXML
	public void Show_cart(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("cart.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Cart");
		StageMgr.STAGES.put("Cart", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Consumer_main").close();
	}
	// Event Listener on Button[#order].onAction
	@FXML
	public void Orders(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("orders.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Orders");
		StageMgr.STAGES.put("Orders", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Consumer_main").close();
	}
	// Event Listener on Button[#ds].onAction
	@FXML
	public void DefaultSort(ActionEvent event) {
		page = 1;
		dir1 = 1;
		dir2 = 1;
		dir3 = 1;
		Sorting_du.DefaultSort();
		this.refresh_goods();
	}
	// Event Listener on Button[#ps].onAction
	@FXML
	public void Sort_by_Price(ActionEvent event) {
		page = 1;
		dir1 = -dir1;
		dir2 = 1;
		dir3 = 1;
		Sorting_du.SortbyPrice(dir1);
		this.refresh_goods();
	}
	// Event Listener on Button[#ss].onAction
	@FXML
	public void Sort_by_Sales(ActionEvent event) {
		page = 1;
		dir1 = 1;
		dir2 = -dir2;
		dir3 = 1;
		Sorting_du.SortbySales(dir2);
		this.refresh_goods();
	}
	// Event Listener on Button[#rs].onAction
	@FXML
	public void Sort_by_Rate(ActionEvent event) {
		page = 1;
		dir1 = 1;
		dir2 = 1;
		dir3 = -dir3;
		Sorting_du.SortbyRate(dir3);
		this.refresh_goods();
	}
}
