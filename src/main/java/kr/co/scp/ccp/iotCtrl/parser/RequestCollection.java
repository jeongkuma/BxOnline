package kr.co.scp.ccp.iotCtrl.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RequestCollection {
	private String Command;
	private ArrayList<ColData> data;

	@Getter
	@Setter
	public static class ColData {
		private String entityid;
		private String model = "RECTIFIER_01";
		private List<Map<String, String>> con;
	}
}
