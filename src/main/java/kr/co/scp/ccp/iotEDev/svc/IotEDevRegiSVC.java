package kr.co.scp.ccp.iotEDev.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevRegiDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevUploadDTO;

public interface IotEDevRegiSVC {

	public int retrieveCtnDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveManfSerialNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveInstNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveUnameDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveUuidDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public String createUuid(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrDev(HttpServletRequest request, TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public String retrieveMaxHldrNo() throws BizException;

	public String retrieveMaxEntrNo() throws BizException;

	public String retrieveMaxAceNo() throws BizException;

	public TbIotEDevRegiDTO retrieveEntrDevInfo(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void updateEntrDev(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public XSSFWorkbook downloadEntrDevList(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public XSSFWorkbook downSmplEDevElsx(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public void uploadBatchEntrDev(HttpServletRequest request) throws BizException;

	public HashMap<String, Object> searchDevImg(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO retrieveDevMdlNm(TbIotEDevUploadDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO retrieveDevClsCdNm(TbIotEDevUploadDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO searchCtnInfo(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;
}
