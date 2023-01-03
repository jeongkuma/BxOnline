package kr.co.scp.ccp.iotCtrl.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RequestControl {
	private String Command;
	private ArrayList<ControlData> data;

	@Getter
	@Setter
	public static class ControlData {
		private String entityid;
		private String model = "RECTIFIER_01";
		private ControlCon con;
	}

	@Getter
	@Setter
	public static class ControlCon {
		private String vo;
		private String dataSendCycle;
		private String cpeSet;
		private String activeKind;
		private String activeType;
		private String activeMode;
		private String onTm;
		private String offTm;
		private String InterruptSts;
		private String activeSts;
		private String electSts;
		private String gpsTm;
		private String acPrintEc;
		private String acMode;
		private String acActionSts;

	}

}
