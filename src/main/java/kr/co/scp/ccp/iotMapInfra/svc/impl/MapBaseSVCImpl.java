package kr.co.scp.ccp.iotMapInfra.svc.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotMapInfra.dto.MapBaseDTO;
import kr.co.scp.ccp.iotMapInfra.svc.MapBaseSVC;
import kr.co.auiot.common.requestor.MapRequestor;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;


@Primary
@Slf4j
@Service
public class MapBaseSVCImpl implements MapBaseSVC {

	@Autowired
	private Environment env = null;

	@Autowired
	private MapRequestor mapRequestor = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map apiCall(MapBaseDTO input) throws BizException {
		ComResponseDto<Map> comResponseDto = new ComResponseDto<Map>();
		String host = env.getProperty("map.host");
		String uri = env.getProperty("map." + input.getApi() + ".path");
		String url = host + uri;
		String svcId = env.getProperty("map.svcId");
		String authKey = env.getProperty("map." + input.getApi() + ".authKey");

		if ("searchPoiNumber".equals(input.getApi())) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01304");
		}

		try {
			// Setter headers
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAll(input.getHeaders());
			httpHeaders.set("svcId", svcId); // Change svcId
			httpHeaders.set("authKey", authKey); // Change authKey

			// Init httpEntity
			HttpEntity httpEntity = new HttpEntity(input.getBody(), httpHeaders);

			// map api call
			ComInfoDto db1 = OmsUtils.addInnerOms(OmsUtils.CHANNEL_MAP, OmsUtils.CHANNEL_TYPE_OUT, input.getApi());
			try {
				comResponseDto = mapRequestor.mapApiCall(url, HttpMethod.POST, httpEntity, Map.class);
			} catch (BizException e) {
				OmsUtils.expInnerOms(db1, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(db1);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return comResponseDto.getData();
	}

}
