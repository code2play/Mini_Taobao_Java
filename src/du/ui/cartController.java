package du.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.List;
import du.biz.Login_du;
import du.biz.Mgr_du;
import du.entity.CartListForUI_du;
import du.helping.CartNode_du;
import du.role.Consumer_du;
import du.role.Seller_du;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
/**
 * 
 * @author 王蒙
 *
 */

public class cartController {
	@FXML
	private Label totalcost;
	@FXML
	private Button back;
	@FXML
	private Button checkout;
	@FXML
	private Label label;
	@FXML
	private ListView listv;
	@FXML
	private Button refresh;
	@FXML
	private ListView listvname;
	@FXML
	private ListView listvprice;
	@FXML
	private ListView listvtotalprice;
	private ObservableList<String> list=FXCollections.observableArrayList();
	
	private ObservableList<String> templist=FXCollections.observableArrayList();

	// Event Listener on Button[#back].onAction
	@FXML
	public void initialize()
	{
		ObservableList<String> listname=FXCollections.observableArrayList();
		ObservableList<String> listprice=FXCollections.observableArrayList();
		ObservableList<String> listtotalprice=FXCollections.observableArrayList();
		listname.add("名称");
		list.add("数量");
		listprice.add("单价");
		listtotalprice.add("总价");
		double sum=0.0;
		List<CartNode_du> g=Consumer_du.getCart().getCartList();
		for(CartNode_du x : g)
		{
			CartListForUI_du temp=new CartListForUI_du(x.getGoodIndex(),x.getNum());			
			String x1=String.valueOf(temp.getNum());			
			String x2=String.valueOf(temp.getPrice());
			String x3=String.valueOf(temp.getTotalPrice());
			listname.add(temp.getName());
			listprice.add(x2);
			list.add(x1);
			listtotalprice.add(x3);
			sum+=temp.getTotalPrice();
		}
		listvname.setItems(listname);
		listvname.getSelectionModel().clearAndSelect(0);
		listvname.setEditable(false);
		listvname.setCellFactory(TextFieldListCell.forListView());
		
		listvprice.setItems(listprice);
		listvprice.getSelectionModel().clearAndSelect(0);
		listvprice.setEditable(false);
		listvprice.setCellFactory(TextFieldListCell.forListView());
		
		listvtotalprice.setItems(listtotalprice);
		listvtotalprice.getSelectionModel().clearAndSelect(0);
		listvtotalprice.setEditable(false);
		listvtotalprice.setCellFactory(TextFieldListCell.forListView());
		
		listv.setItems(list);
		listv.getSelectionModel().clearAndSelect(0);
		listv.setEditable(true);
		listv.setCellFactory(TextFieldListCell.forListView());
		
		
		String x4=String.valueOf(sum);
		totalcost.setText(x4);

	}
	public void calculate(ActionEvent event) throws IOException{
		String ans = Consumer_du.getCart().checkOut();
		System.out.println(ans);
		label.setText(ans);
		
		if(ans.equals("结算成功")){
			StageMgr.STAGES.remove("Consumer_main");
			Stage stage=new Stage();
			Parent root = null;
			Login_du.load_consumer(Consumer_du.getUser_name());
			root = FXMLLoader.load(getClass().getResource("Consumer_main.fxml"));
			stage.setTitle("Consumer");
			StageMgr.STAGES.put("Consumer_main", stage);
			stage.setScene(new Scene(root));
		}
	}
	
	public void Goback(ActionEvent event){
		StageMgr.STAGES.get("Cart").close();
		StageMgr.STAGES.remove("Cart");
		StageMgr.STAGES.get("Consumer_main").show();
	}
	

	public void setOnEditCommit(ActionEvent event) throws IOException
	{
		list=listv.getItems();
		int i=1;
		int a=0;
		for(CartNode_du c:Consumer_du.getCart().getCartList()){
			a=Integer.parseInt(list.get(i));
			
			boolean changed = Consumer_du.getCart().changeNum(c.getGoodIndex(), a);
			if (changed == false) {
				
			}
			if (Consumer_du.getCart().getCartList().size() == 0)
				break;
			
			i++;
		}
		Mgr_du.save_consumer();
		
		StageMgr.STAGES.get("Cart").close();
		StageMgr.STAGES.remove("Cart");
		Stage stage=new Stage();
		Parent root = null;
		root = FXMLLoader.load(getClass().getResource("cart.fxml"));
		stage.setTitle("Cart");
		StageMgr.STAGES.put("Cart", stage);
		stage.setScene(new Scene(root));
		stage.show();
		
		//initialize();


	}
}