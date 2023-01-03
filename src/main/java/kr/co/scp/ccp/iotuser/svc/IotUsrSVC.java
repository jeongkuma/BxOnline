package kr.co.scp.ccp.iotuser.svc;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrDTO;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrRDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import org.springframework.context.annotation.Primary;


public interface IotUsrSVC {

	public HashMap<String, Object> retrieveIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException ;

	public TbIotUsrRDTO retrieveIotUsrBySeq(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public void updateIotUsrInfo(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public String retrieveDuplicationLgnId(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public void createIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public void createIotUsrAll(HttpServletRequest request) throws BizException;

	public void updateIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public XSSFWorkbook excelCreate() throws BizException;

	public List<String> retrieveIotUsrAdminRole(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public int retrieveIotUsrPwChk(TbIotUsrDTO tbIotUseDTO) throws BizException;

	public List<TbIotCmCdDTO> retrieveIotUsrRoleList() throws BizException;

	public List<TbIotUsrDTO> retrieveUsrListByCust(String custSeq) throws BizException;

	public List<TbIotUsrDTO> retrieveUsrPhoneDuplChk(TbIotUsrDTO tbIotUsrDTO) throws BizException;
}
