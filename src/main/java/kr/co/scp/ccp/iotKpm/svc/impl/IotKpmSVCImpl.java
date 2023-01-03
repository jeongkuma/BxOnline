package kr.co.scp.ccp.iotKpm.svc.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.kpm.IrV150;
import com.kpm.SrV150;
import com.kpm.TbV120;
import com.kpm.TbV150;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotKpm.svc.IotKpmSVC;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Primary
@Service
public class IotKpmSVCImpl implements IotKpmSVC {

    @Override
    public HashMap<String, Object> processKpmParse(Map<String, Object> param) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        String modelCd = (String) param.get("devMdlCd");
        String modelNm = (String) param.get("devMdlNm");
        String con = (String) param.get("con");

        List<Map<String, Object>> kpmConList = null;

        JsonArray dataArray;

        String value = "";

        try {

            if (modelNm.equals("TB_V120")) {
                TbV120 tbV120 = new TbV120();
                dataArray = tbV120.getTb01PacketData(con);
                String kpmParseText = dataArray.toString();
                value = kpmParseText;
                kpmConList = new Gson().fromJson(kpmParseText, new TypeToken<List<Map<String, Object>>>(){}.getType());
            } else if (modelNm.equals("TB_V150")) {
                TbV150 tbV150 = new TbV150();
                dataArray = tbV150.getTbPacketData(con);
                String kpmParseText = dataArray.toString();
                value = kpmParseText;
                kpmConList = new Gson().fromJson(kpmParseText, new TypeToken<List<Map<String, Object>>>(){}.getType());

            } else if (modelNm.equals("S-VALVE")) {
                SrV150 srV150 = new SrV150();
                dataArray = srV150.getSrPacketData(con);
                String kpmParseText = dataArray.toString();
                value = kpmParseText;
                kpmConList = new Gson().fromJson(kpmParseText, new TypeToken<List<Map<String, Object>>>(){}.getType());

            } else if (modelNm.equals("I-VALVE")) {
                IrV150 irV150 = new IrV150();
                dataArray = irV150.getIrPacketData(con);
                String kpmParseText = dataArray.toString();
                value = kpmParseText;
                kpmConList = new Gson().fromJson(kpmParseText, new TypeToken<List<Map<String, Object>>>(){}.getType());

            }

        } catch (Exception e) {
            throw new BizException("kpmParsingError");
        }

        resultMap.put("parsedData", value);
        resultMap.put("parsedObject", kpmConList);

        return resultMap;

//        String mockUp = "[\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 00:46:14\",\n" +
//                "    \"cpe\": -1409,\n" +
//                "    \"ac\": 664,\n" +
//                "    \"bat\": 3611,\n" +
//                "    \"temp\": 26.9,\n" +
//                "    \"humi\": 60.2\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 01:46:14\",\n" +
//                "    \"cpe\": -1428,\n" +
//                "    \"ac\": 651,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.8,\n" +
//                "    \"humi\": 60.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 02:46:14\",\n" +
//                "    \"cpe\": -1432,\n" +
//                "    \"ac\": 577,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.7,\n" +
//                "    \"humi\": 60.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 03:46:14\",\n" +
//                "    \"cpe\": -1436,\n" +
//                "    \"ac\": 539,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.6,\n" +
//                "    \"humi\": 60.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 04:46:14\",\n" +
//                "    \"cpe\": -1424,\n" +
//                "    \"ac\": 653,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.5,\n" +
//                "    \"humi\": 60.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 05:46:14\",\n" +
//                "    \"cpe\": -1428,\n" +
//                "    \"ac\": 474,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.4,\n" +
//                "    \"humi\": 60.4\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 06:46:14\",\n" +
//                "    \"cpe\": -1420,\n" +
//                "    \"ac\": 480,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.3,\n" +
//                "    \"humi\": 60.4\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 07:46:14\",\n" +
//                "    \"cpe\": -1443,\n" +
//                "    \"ac\": 605,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.3,\n" +
//                "    \"humi\": 60.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 08:46:14\",\n" +
//                "    \"cpe\": -1523,\n" +
//                "    \"ac\": 795,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 26.5,\n" +
//                "    \"humi\": 60.2\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 09:46:14\",\n" +
//                "    \"cpe\": -1474,\n" +
//                "    \"ac\": 865,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 27,\n" +
//                "    \"humi\": 59.9\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 10:46:14\",\n" +
//                "    \"cpe\": -1493,\n" +
//                "    \"ac\": 781,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 27.9,\n" +
//                "    \"humi\": 59.3\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"datetime\": \"2020-08-13 11:46:14\",\n" +
//                "    \"cpe\": -1424,\n" +
//                "    \"ac\": 830,\n" +
//                "    \"bat\": 3613,\n" +
//                "    \"temp\": 28.4,\n" +
//                "    \"humi\": 59.1\n" +
//                "  }\n" +
//                "]";
//        kpmConList = new Gson().fromJson(mockUp, new TypeToken<List<Map<String, Object>>>(){}.getType());
    }
}
