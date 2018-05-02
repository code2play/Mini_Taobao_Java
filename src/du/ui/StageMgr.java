package du.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;

public class StageMgr {
	public static Map<String, Stage> STAGES=new HashMap<String, Stage>();
	public static int index;
	public static int id_in_db;
	public static boolean go_back_to_orders = false;
}
