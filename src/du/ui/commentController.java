package du.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

import du.biz.Mgr_du;
import du.entity.Goods_du;
import du.helping.Comments_du;
import du.role.Consumer_du;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

public class commentController {
	@FXML
	private TextArea comments;
	@FXML
	private TextField rate;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private Label label;

	// Event Listener on Button[#ok].onAction
	@FXML
	public void Ok(ActionEvent event) {
		String r = rate.getText();
		if(r.equals("")){
			label.setText("请输入0~5之间的数字");
			return;
		}
		double rate = Double.valueOf(r);
		String c = comments.getText();
		if(c.equals("")){
			label.setText("评论不能为空");
			return;
		} else if (rate<0 || rate>5){
			label.setText("请输入0~5之间的数字");
			return;
		} else {
			int cid = Mgr_du.add_comment(c);
			Goods_du g = Mgr_du.get_goods(StageMgr.id_in_db);
	        Comments_du cc = Mgr_du.get_comment(cid);
	        g.addComment(cc);
			g.Rate(rate);
			Mgr_du.save_goods(g);
		}
		label.setText("");
		StageMgr.STAGES.get("Comment").close();
		StageMgr.STAGES.remove("Comment");
		StageMgr.STAGES.get("Orders").show();
	}
	// Event Listener on Button[#cancel].onAction
	@FXML
	public void Cancel(ActionEvent event) {
		StageMgr.STAGES.get("Comment").close();
		StageMgr.STAGES.remove("Comment");
		StageMgr.STAGES.get("Orders").show();
	}
}
