package du.ui;

import java.io.IOException;

import du.biz.Login_du;
import du.biz.Mgr_du;
import du.role.Seller_du;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.TextArea;

import javafx.scene.text.Font;
import javafx.stage.Stage;

public class new_goodsController {
	@FXML
	private Font x1;
	@FXML
	private TextArea description;
	@FXML
	private TextField name;
	@FXML
	private TextField price;
	@FXML
	private TextField amounts;
	@FXML
	private Button save;
	@FXML
	private Button cancel;
	@FXML
	private Label label;
	
	public void initialize(){
		name.setEditable(true);
		price.setEditable(true);
		amounts.setEditable(true);
		description.setEditable(true);
	}
	
	public void save(ActionEvent event) throws IOException{
		if(name.getText().equals("") || price.getText().equals("") || amounts.getText().equals("")){
			label.setText("商品名称、价格、库存不能为空");
			return;
		}
		String n = name.getText();
		double p = Double.valueOf(price.getText()).doubleValue();
		int s = Integer.valueOf(amounts.getText()).intValue();
		String d = description.getText();
		int id = Mgr_du.add_goods(n, p, Seller_du.getShop().getIndex(), s, d);
		Seller_du.getShop().add_goods(id);
		Mgr_du.save_shop();
		
		StageMgr.STAGES.remove("Seller_main");
		Login_du.load_seller(Seller_du.getUser_name());
		Stage stage=new Stage();
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("Seller_main.fxml"));
		stage.setTitle("Seller");
		StageMgr.STAGES.put("Seller_main", stage);
		stage.setScene(new Scene(root));
		StageMgr.STAGES.get("New_goods").close();
		StageMgr.STAGES.remove("New_goods");
		stage.show();
	}
	
	public void cancel(ActionEvent event){
		StageMgr.STAGES.get("New_goods").close();
		StageMgr.STAGES.remove("New_goods");
		StageMgr.STAGES.get("Seller_main").show();
	}

}
