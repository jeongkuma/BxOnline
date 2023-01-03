package kr.co.scp.ccp.iotEDev.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetAttbDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetRcvUsrDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetSmsDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEdevDetSmsUsrDTO;

import java.util.HashMap;

public interface IotEDevDetSmsSVC {

	public HashMap<String, Object> retrieveEDevDetSmsList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException; 
	
	public HashMap<String, Object> retrieveEDevDetDevAttbList(TbIotEdevDetAttbDTO tbIotEdevDetAttbDTO) throws BizException; 
	
	public HashMap<String, Object> retrieveEDevDetUserList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException; 

	public HashMap<String, Object> retrieveEDevDetUserListByDetSetSeq(TbIotEdevDetRcvUsrDTO tbIotEdevDetRcvUsrDTO) throws BizException; 
			
	public void createEDevDetSms(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException;
	
	public void deleteEDevDetRcvUsr(TbIotEdevDetSmsUsrDTO tbIotEdevDetSmsUsrDTO) throws BizException;

	public HashMap<String, Object> retrieveEDevDetMessageList(TbIotEdevDetSmsDTO tbIotEdevDetSmsDTO) throws BizException; 
	
	
}
