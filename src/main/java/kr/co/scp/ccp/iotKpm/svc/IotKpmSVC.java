package kr.co.scp.ccp.iotKpm.svc;

import java.util.HashMap;
import java.util.Map;

public interface IotKpmSVC {

    HashMap<String, Object> processKpmParse(Map<String, Object> param);
}
