package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

import du.biz.Login_du;
import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.helping.Comments_du;
import du.role.Seller_du;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.text.Font;
import javafx.stage.Stage;

public class goods_sellerController {
	@FXML
	private Font x1;
	@FXML
	private Button back;
	@FXML
	private TextField name;
	@FXML
	private TextField price;
	@FXML
	private TextField selling_amounts;
	@FXML
	private TextArea description;
	@FXML
	private TextField amounts;
	@FXML
	private TextField rate;
	@FXML
	private TextArea comments;
	@FXML
	private Button change;
	@FXML
	private Button save;
	@FXML
	private Button delete;
	
	private Goods_du g;

	public void goback(ActionEvent event) {
		StageMgr.STAGES.get("Goods_seller").close();
		StageMgr.STAGES.remove("Goods_seller");
		StageMgr.STAGES.get("Seller_main").show();
	}
	
	public void initialize()
	{
		g = Mgr_du.goods_from_shop.get(StageMgr.index);
		name.setEditable(false);
		price.setEditable(false);
		selling_amounts.setEditable(false);
		description.setEditable(false);
		amounts.setEditable(false);
		rate.setEditable(false);
		comments.setEditable(false);
		name.setText(g.getName());
		Double x1=new Double(g.getPrice());
		price.setText(x1.toString());		
		String x2=String.valueOf(g.getStock());
		selling_amounts.setText(x2);		
		int shopid=g.getShop();
		String seller_name=Mgr_du.get_shop(shopid).getName();
		String x3=String.valueOf(g.getSales());
		amounts.setText(x3);
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
		change.setDisable(false);
		save.setDisable(true);
	}
	
	public void save(ActionEvent event) throws IOException
	{
		Goods_du g = Mgr_du.goods_from_shop.get(StageMgr.index);
		name.setEditable(true);
		price.setEditable(true);
		selling_amounts.setEditable(true);
		description.setEditable(true);
		amounts.setEditable(false);
		rate.setEditable(false);
		comments.setEditable(false);
		g.setName(name.getText());
		double x1=Double.valueOf(price.getText()).doubleValue();
		g.setPrice(x1);
		int x2=Integer.valueOf(selling_amounts.getText()).intValue();
		g.setStock(x2);
		g.setDescription(description.getText());
		Mgr_du.save_goods(g);
		this.initialize();
		
		StageMgr.STAGES.remove("Seller_main");
		Login_du.load_seller(Seller_du.getUser_name());
		Stage stage=new Stage();
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("Seller_main.fxml"));
		stage.setTitle("Seller");
		StageMgr.STAGES.put("Seller_main", stage);
		stage.setScene(new Scene(root));
	}
	
	public void change(ActionEvent event)
	{
		name.setEditable(true);
		price.setEditable(true);
		selling_amounts.setEditable(true);
		description.setEditable(true);
		amounts.setEditable(false);
		rate.setEditable(false);
		comments.setEditable(false);
		change.setDisable(true);
		save.setDisable(false);
	}
	// Event Listener on Button[#delete].onAction
	@FXML
	public void delete(ActionEvent event) throws IOException {
		Mgr_du.remove_goods(g.getIndex());
		Seller_du.getShop().remove_goods(g.getIndex());
		Mgr_du.save_shop();
		Mgr_du.save_seller();
		StageMgr.STAGES.get("Goods_seller").close();
		StageMgr.STAGES.remove("Goods_seller");
		
		StageMgr.STAGES.remove("Seller_main");
		Login_du.load_seller(Seller_du.getUser_name());
		Stage stage=new Stage();
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("Seller_main.fxml"));
		stage.setTitle("Seller");
		StageMgr.STAGES.put("Seller_main", stage);
		stage.setScene(new Scene(root));
		stage.show();
	}
}
