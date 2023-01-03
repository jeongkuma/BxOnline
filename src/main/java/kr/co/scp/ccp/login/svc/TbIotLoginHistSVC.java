package kr.co.scp.ccp.login.svc;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistDTO;

public interface TbIotLoginHistSVC {

	// 로그인 이력 조회  아이디 
		public HashMap<String, Object> retrieveTbIotLoginHist(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException;
		
		XSSFWorkbook excelCreate(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException;
}
