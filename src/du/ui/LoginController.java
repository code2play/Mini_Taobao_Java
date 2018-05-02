package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import du.biz.Login_du;
import du.biz.Mgr_du;
import du.role.Consumer_du;
import du.role.Seller_du;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import javafx.scene.control.PasswordField;
/**
 * 
 * @author TJH
 *
 */
public class LoginController {
	@FXML
	private AnchorPane LoginStage;
	@FXML
	private Button login;
	@FXML
	private TextField user_name;
	@FXML
	private Button forget_password;
	@FXML
	private Button new_account;
	@FXML
	private Label label;
	@FXML
	private PasswordField password;
	@FXML
	private RadioButton consumer;
	@FXML
	private ToggleGroup type;
	@FXML
	private RadioButton seller;
	private Login_du l = new Login_du();
	private Mgr_du mgr = new Mgr_du();

	// Event Listener on Button[#login].onAction
	@FXML
	public void check(ActionEvent event) throws Exception {
		String un = user_name.getText();
		String psw = password.getText();
		if(un.equals("") || psw.equals(""))
			label.setText("�û��������벻��Ϊ��!");
		else if(l.check(un, psw)==0)
			label.setText("�û���������!");
		else if(l.check(un, psw)==1)
			label.setText("�������!");
		else if(l.check(un, psw)==2){
			label.setText("");
			Stage stage=new Stage();
			Parent root = null;
			if(Login_du.isConsumer()){
				Login_du.load_consumer(un);
				root = FXMLLoader.load(getClass().getResource("Consumer_main.fxml"));
				stage.setTitle("Consumer");
				StageMgr.STAGES.put("Consumer_main", stage);
			}else{
				Login_du.load_seller(un);
				root = FXMLLoader.load(getClass().getResource("Seller_main.fxml"));
				stage.setTitle("Seller");
				StageMgr.STAGES.put("Seller_main", stage);
			}
			stage.setScene(new Scene(root));
			stage.show();
			StageMgr.STAGES.get("Login").close();
		}
	}
	// Event Listener on Button[#new_account].onAction
	@FXML
	public void new_account(ActionEvent event) {
		String un = user_name.getText();
		String psw = password.getText();
		int flag = 0;
		if(un.equals("") || psw.equals("")){
			label.setText("�û��������벻��Ϊ��!");
			return;
		}
		else if(psw.length()<6){
			label.setText("��������6λ!");
			return;
		}
		else flag=l.new_account(un,psw);
		if(flag==1) label.setText("�ɹ������˻�");
		else label.setText("����ʧ�ܣ��û����Ѵ���");
	}
	// Event Listener on RadioButton[#consumer].onAction
	@FXML
	public void Type1(ActionEvent event) {
		Login_du.setConsumer(true);
		Login_du.setSeller(false);
		//System.out.println(l.isConsumer() + "\t" + l.isSeller());
	}
	// Event Listener on RadioButton[#seller].onAction
	@FXML
	public void Type2(ActionEvent event) {
		Login_du.setConsumer(false);
		Login_du.setSeller(true);
		//System.out.println(l.isConsumer() + "\t" + l.isSeller());
	}
}
