package du.ui;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.entity.Order_du;
import du.helping.CartNode_du;
import du.role.Seller_du;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

public class seller_sellingrecordsController {
	@FXML
	private ListView<String> goodsname;
	@FXML
	private ListView<String> amounts;
	@FXML
	private ListView<String> consumername;
	@FXML
	private ListView<String> address;
	@FXML
	private ListView<String> date;
	@FXML
	private Button back;
	
	public void initialize()
	{
		ObservableList<String> listgoods=FXCollections.observableArrayList();//finish
		ObservableList<String> listamounts=FXCollections.observableArrayList();
		ObservableList<String> listconsumer=FXCollections.observableArrayList();//finish
		ObservableList<String> listaddress=FXCollections.observableArrayList();//finish
		ObservableList<String> listdate=FXCollections.observableArrayList();//finish
		listgoods.add("��Ʒ����");
		listamounts.add("����");
		listconsumer.add("���");
		listaddress.add("�ջ���ַ");
		listdate.add("�µ�ʱ��");
		int i=1;
		int flag;
		int number;
		for(Order_du o : Seller_du.getShop().getOrders()){
			for(CartNode_du c : o.getOrderList()){
				listaddress.add(o.getAddress());
				listdate.add(o.getDate());
				listconsumer.add(o.getBuyer());
				flag=c.getGoodIndex();
				Goods_du  g = Mgr_du.get_goods(flag);
				listgoods.add(g.getName());
				number=c.getNum();
				listamounts.add(String.valueOf(number));
			}
		}

		goodsname.setItems(listgoods);
		goodsname.getSelectionModel().clearAndSelect(0);
		goodsname.setEditable(false);
		goodsname.setCellFactory(TextFieldListCell.forListView());
		
		amounts.setItems(listamounts);
		amounts.getSelectionModel().clearAndSelect(0);
		amounts.setEditable(false);
		amounts.setCellFactory(TextFieldListCell.forListView());
		
		consumername.setItems(listconsumer);
		consumername.getSelectionModel().clearAndSelect(0);
		consumername.setEditable(false);
		consumername.setCellFactory(TextFieldListCell.forListView());
		
		address.setItems(listaddress);
		address.getSelectionModel().clearAndSelect(0);
		address.setEditable(false);
		address.setCellFactory(TextFieldListCell.forListView());
		
		date.setItems(listdate);
		date.getSelectionModel().clearAndSelect(0);
		date.setEditable(false);
		date.setCellFactory(TextFieldListCell.forListView());
		
		
	}
	public void goback(ActionEvent event)
	{
		StageMgr.STAGES.get("Selling_records").close();
		StageMgr.STAGES.remove("Selling_records");
		StageMgr.STAGES.get("Seller_main").show();
	}

}
