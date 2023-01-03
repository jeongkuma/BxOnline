package kr.co.scp.ccp.iotTerms.svc;

import java.util.HashMap;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTerms.dto.TbIotTermsDTO;

public interface TbIotTermsSVC {

	public HashMap<String, Object> retrieveTerms(TbIotTermsDTO termsDto) throws BizException;

	public void createTermsAgr(TbIotTermsDTO termsDto) throws BizException;
}
