package kr.co.scp.ccp.iotEDev.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotEDev.dto.DownEntrDevDTO;
import kr.co.scp.ccp.iotEDev.dto.EDevCurValDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevRegiDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevUploadDTO;

@Mapper
public interface IotEDevRegiDAO {

	public int retrieveCtnDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int deleteEntrDevByCtn(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveManfSerialNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveInstNoDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveUuidDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int retrieveUnameDup(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrDev(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrDevInsLoc(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrDevCurVal(EDevCurValDTO eDevCurValDTO) throws BizException;

	public void updateEntrDev(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void updateEntrDevInsLoc(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void updateEntrDevCurVal(EDevCurValDTO eDevCurValDTO) throws BizException;

	public String retrieveMaxHldrNo() throws BizException;

	public String retrieveMaxEntrNo() throws BizException;

	public String retrieveMaxAceNo() throws BizException;

	public TbIotEDevRegiDTO retrieveEntrDev(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public List<EDevCurValDTO> retrieveEntrDevCurVal(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public List<DownEntrDevDTO> getEntrDevDownLoadList(TbIotEDevDTO tbIotEDevDTO) throws BizException;

	public int chkEntrInstLoc(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public String retrieveDevSeq() throws BizException;

	public TbIotEDevRegiDTO searchDevImg(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrSDevAttbCurVal(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public void createEntrDevColVal(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO retrieveDevMdlNm(TbIotEDevUploadDTO dto) throws BizException;

	public List<TbIotEDevRegiDTO> retrieveDevClsCdNm(TbIotEDevUploadDTO dto) throws BizException;

	public TbIotEDevRegiDTO searchCtnInfo(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public int chkEntrDevCurVal(EDevCurValDTO eDevCurValDTO) throws BizException;

	public int chkEntrDevColVal(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO updateDevImg(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public TbIotEDevRegiDTO retrieveEntrDevDoor(TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException;

	public List<DownEntrDevDTO> getEntrDevDoorDownLoadList(TbIotEDevDTO tbIotEDevDTO) throws BizException;

}
