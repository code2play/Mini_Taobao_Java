package du.ui;

import javafx.fxml.FXML;
/**
 * 
 * @author angmeng
 *
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.entity.Shop_du;
import du.helping.Comments_du;
import du.helping.Sorting_du;
import du.role.Consumer_du;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class goodsController {
	@FXML
	private Font x1;
	@FXML
	private Label name;
	@FXML
	private Label price;
	@FXML
	private Label amounts;
	@FXML
	private Label seller;
	@FXML
	private Label selling_amounts;
	@FXML
	private TextArea comments;
	@FXML
	private Label rate;
	@FXML
	private Label label;
	@FXML
	private TextArea description;
	@FXML
	private Button addToCart;
	@FXML
	private Button back;
	@FXML
	private Button comment;
	@FXML
	private TextField number;
	Mgr_du mgr = new Mgr_du();
	
	public void initialize(){
		Goods_du g = null;
		if(StageMgr.go_back_to_orders)
			g = Mgr_du.get_goods(StageMgr.id_in_db);
		else g = Sorting_du.resultList.get(StageMgr.index);
		StageMgr.id_in_db = g.getIndex();
		
		name.setText(g.getName());
		Double x1=new Double(g.getPrice());
		price.setText(x1.toString());
		
		String x2=String.valueOf(g.getStock());
		amounts.setText(x2);
		
		int shopid=g.getShop();
		String seller_name=Mgr_du.get_shop(shopid).getName();
		seller.setText(seller_name);
		
		String x3=String.valueOf(g.getSales());
		selling_amounts.setText(x3);
		
		Double x4=new Double(g.getRate());
		rate.setText(x4.toString());
		
		description.setText(g.getDescription());
		
		String temp = new String();
		for(Comments_du c : g.getComments()){
			temp+=c.getAuthor();
			temp+="\t";
			temp+=c.getTimeAndDate();
			temp+="\t";
			temp+=c.getComment();
			temp+="\n";
		}
		comments.setText(temp);
		
		if(StageMgr.go_back_to_orders){
			comment.setDisable(false);
			comment.setVisible(true);
		}
		else{
			comment.setDisable(true);
			comment.setVisible(false);
		}
	}
	public void addToCart(ActionEvent event){
		String k;
		int i;
		k=number.getText();
		if(k.equals(""))
			i=1;
		else 
			i=Integer.parseInt(k);
		if(i<=0) {
			label.setText("请输入大于0的整数");
			return;
		}
		boolean ans = Consumer_du.getCart().addGoods(StageMgr.id_in_db,i);
		System.out.println(ans);
		if(ans){
			label.setText("添加成功");
			Mgr_du.save_consumer();
		}
		else label.setText("添加失败，商品库存不足");
	}
	// Event Listener on Button[#back].onAction
	@FXML
	public void goback(ActionEvent event) {
		StageMgr.STAGES.get("Goods").close();
		StageMgr.STAGES.remove("Goods");
		if(StageMgr.go_back_to_orders)
			StageMgr.STAGES.get("Orders").show();
		else
			StageMgr.STAGES.get("Consumer_main").show();
	}
	// Event Listener on Button[#comment].onAction
	@FXML
	public void Comment(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("comment.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Comment");
		StageMgr.STAGES.put("Comment", stage);
		stage.setScene(new Scene(root));
		stage.show();
		StageMgr.STAGES.get("Goods").close();
	}
}
