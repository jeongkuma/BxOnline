package kr.co.scp.ccp.iotCust.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustCombineDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustSDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface IotCustSVC {

	public HashMap<String, Object> retrieveIotCust(TbIotCustDTO tbIotCustDTO) throws BizException;

	public HashMap<String, Object> retrieveIotCustAll(TbIotCustDTO tbIotCustDTO) throws BizException;

	public TbIotEDevDTO retrieveCustInfoByCtn(TbIotCustDTO tbIotCustDTO) throws BizException;

	public HashMap<String, Object> retrieveIotCustBySeq(TbIotCustUDTO tbIotCustDTO) throws BizException;

	public void createIoTCust(TbIotCustCombineDTO tbIotCustDTO, HttpServletRequest request) throws BizException;

	public void updateIotCust(HttpServletRequest request, TbIotCustDTO tbIotCustDTO) throws BizException;

	public void deleteIotCust(TbIotCustDTO tbIotCustDTO) throws BizException;

	public void rejoinIotCust(TbIotCustDTO tbIotCustDTO) throws BizException;

	public String retrieveDuplicatedCustId(TbIotCustDTO tbIotCustDTO) throws BizException;

	public String retrieveDuplicatedCustNm(TbIotCustDTO tbIotCustDTO) throws BizException;

	public TbIoTBrdFileListDTO getFile(TbIoTBrdFileListDTO tbIoTBrdFileDTO) throws BizException;

	public TbIotSvcDto retrieveIotCustSvc() throws BizException;

	public List<TbIotCustSDTO> retrieveIotCustSelect(TbIotCustSDTO tbIotCustSDTO)throws BizException;

}

