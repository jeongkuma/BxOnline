package kr.co.scp.ccp.iotCtrl.parser;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ControlParse {
	private Gson GSON = new Gson();

	/**
	 * 정류기 즉시 상태 조회 데이터 파싱
	 * OSB로 보낼 파람 파싱
	 * @param parameters
	 * @return
	 */
	public HashMap<String, Object> DataCollectionRequestParser(Map<String, Object> parameters) {
		String gsonStr = "";
		String nodeId = "";
		RequestCollection reqData = new RequestCollection();
		ArrayList<RequestCollection.ColData> datas = new ArrayList<RequestCollection.ColData>();
		RequestCollection.ColData data = new RequestCollection.ColData();
		HashMap<String, Object> parsingMap = new HashMap<String, Object>();

		try {
			nodeId = String.valueOf(parameters.get("nodeId"));
			data.setEntityid(nodeId);

			datas.add(data);

			reqData.setCommand("82");
			reqData.setData(datas);

			gsonStr = GSON.toJson(reqData);
			// Map 캐스팅
			parsingMap = GSON.fromJson(gsonStr, parsingMap.getClass());

			log.info("nodeId : {}, param to json : {}", nodeId, gsonStr);
		} catch (Exception e) {
			log.error("ERROR!! DataCollectionRequestParser - parameters : {}, msg : {}", parameters.toString(), e.getMessage());
		}

		return parsingMap;
	}

	/**
	 * 정류기 제어 데이터 파싱
	 * 제어기로 보낼 파람 파싱
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String DataColtrollRequestParser(Map<String, Object> parameters) {
		String gsonStr = "";
		String nodeId = "";

		RequestControl reqData = new RequestControl();
		ArrayList<RequestControl.ControlData> datas = new ArrayList<RequestControl.ControlData>();
		RequestControl.ControlData data = new RequestControl.ControlData();
		RequestControl.ControlCon con = new RequestControl.ControlCon();

		try {
			nodeId = String.valueOf(parameters.get("nodeId"));
			List<Map<String, Object>> controlList = (List<Map<String, Object>>) parameters.get("control");

			for(Map<String, Object> item : controlList) {
				data.setEntityid(nodeId);

				String vo = String.valueOf(item.get("vo"));
				String dataSendCycle = String.valueOf(item.get("dataSendCycle"));
				String cpeSet = String.valueOf(item.get("cpeSet"));
				String activeKind = String.valueOf(item.get("activeKind"));
				String activeType = String.valueOf(item.get("activeType"));
				String activeMode = String.valueOf(item.get("activeMode"));
				String onTm = String.valueOf(item.get("onTm"));
				String offTm = String.valueOf(item.get("offTm"));
				String InterruptSts = String.valueOf(item.get("InterruptSts"));
				String activeSts = String.valueOf(item.get("activeSts"));
				String electSts = String.valueOf(item.get("electSts"));
				String gpsTm = String.valueOf(item.get("gpsTm"));
				String acPrintEc = String.valueOf(item.get("acPrintEc"));
				String acMode = String.valueOf(item.get("acMode"));
				String acActionSts = String.valueOf(item.get("acActionSts"));

				if(!StringUtils.isBlank(vo))
					con.setVo(vo);
				if(!StringUtils.isBlank(dataSendCycle))
					con.setDataSendCycle(dataSendCycle);
				if(!StringUtils.isBlank(cpeSet))
					con.setCpeSet(cpeSet);
				if(!StringUtils.isBlank(activeKind))
					con.setActiveKind(activeKind);
				if(!StringUtils.isBlank(activeType))
					con.setActiveType(activeType);
				if(!StringUtils.isBlank(activeMode))
					con.setActiveMode(activeMode);
				if(!StringUtils.isBlank(onTm))
					con.setOnTm(onTm);
				if(!StringUtils.isBlank(offTm))
					con.setOffTm(offTm);
				if(!StringUtils.isBlank(InterruptSts))
					con.setInterruptSts(InterruptSts);
				if(!StringUtils.isBlank(activeSts))
					con.setActiveSts(activeSts);
				if(!StringUtils.isBlank(electSts))
					con.setElectSts(electSts);
				if(!StringUtils.isBlank(gpsTm))
					con.setGpsTm(gpsTm);
				if(!StringUtils.isBlank(acPrintEc))
					con.setAcPrintEc(acPrintEc);
				if(!StringUtils.isBlank(acMode))
					con.setAcMode(acMode);
				if(!StringUtils.isBlank(acActionSts))
					con.setAcActionSts(acActionSts);

				data.setCon(con);

				datas.add(data);
			}

			reqData.setCommand("52");
			reqData.setData(datas);

			gsonStr = GSON.toJson(reqData);

			log.info("nodeId : {}, param to json : {}", nodeId, gsonStr);
		} catch (Exception e) {
			log.error("ERROR!! DataColtrollRequestParser - parameters : {}, msg : {}", parameters.toString(), e.getMessage());
		}

		return gsonStr;
	}
}
