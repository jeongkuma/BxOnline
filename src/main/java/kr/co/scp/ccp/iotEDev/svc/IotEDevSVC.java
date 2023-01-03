package kr.co.scp.ccp.iotEDev.svc;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEDev.dto.EDevSrchResDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDetHistDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;

public interface IotEDevSVC {

	public EDevSrchResDTO retrieveIotEDev(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public List<TbIotEDevDTO> retrieveIotEDevAttb(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public List<TbIotEDevDTO> retrieveIotEDevAddr(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public List<TbIotEDevDTO> retrieveIotEDevSend(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public List<TbIotEDetHistDTO> retrieveTemplateList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException;

	public List<TbIotEDetHistDTO> retrieveIotDevCls() throws BizException;

	public List<TbIotEDetHistDTO> retrieveIotDetStatus() throws BizException;

	public List<TbIotEDetHistDTO> retrieveDevMdlList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException;

	public HashMap<String, Object> retrieveIotEntrDevDetList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException;

	public XSSFWorkbook downloadExcelEntrDevDetList(TbIotEDetHistDTO tbIotEDetHistDTO) throws Exception;

	// public XSSFWorkbook downloadEntrDevList(TbIotEDevDTO tbIotEDevDTO);

	public EDevSrchResDTO retrieveIotEDevDoor(TbIotEDevDTO tbIotEDevDTO) throws BizException;

}
